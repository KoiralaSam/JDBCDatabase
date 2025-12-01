/**
 * Author: Lon Smith, Ph.D.
 * Description: This is the framework for the database program. Additional requirements and functionality
 *    are to be built by you and your group.
 */

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Frame;
import javax.swing.JTextField;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JOptionPane;

public class EmployeeSearchFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtDatabase;
	private JList<String> lstDepartment;
	private DefaultListModel<String> department = new DefaultListModel<String>();
	private JList<String> lstProject;
	private DefaultListModel<String> project = new DefaultListModel<String>();
	private JTextArea textAreaEmployee;
	private JCheckBox chckbxNotDept;
	private JCheckBox chckbxNotProject;
	private String databaseName;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EmployeeSearchFrame frame = new EmployeeSearchFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public EmployeeSearchFrame() {
		setTitle("Employee Search");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 347);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Database:");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 12));
		lblNewLabel.setBounds(21, 23, 59, 14);
		contentPane.add(lblNewLabel);
		
		txtDatabase = new JTextField();
		txtDatabase.setBounds(90, 20, 193, 20);
		contentPane.add(txtDatabase);
		txtDatabase.setColumns(10);
		
		JButton btnDBFill = new JButton("Fill");
		/**
		 * The btnDBFill should fill the department and project JList with the 
		 * departments and projects from your entered database name.
		 */
		btnDBFill.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				databaseName = txtDatabase.getText();

				department.clear();
				project.clear();

				DatabaseReturn deptReturn = FillDatabase.GetDepartment(databaseName);
				if (deptReturn.errorMessage.isEmpty()) {
					for(String dept_name: deptReturn.data) {
						department.addElement(dept_name);
					}
				} else {
					System.err.println("Error loading departments: " + deptReturn.errorMessage);
					JOptionPane.showMessageDialog(EmployeeSearchFrame.this, deptReturn.errorMessage);
				}
				
				DatabaseReturn prjReturn = FillDatabase.GetProjects(databaseName);
				if (prjReturn.errorMessage.isEmpty()) {
					for(String prj_name : prjReturn.data) {
						project.addElement(prj_name);
					}
				} else {
					System.err.println("Error loading projects: " + prjReturn.errorMessage);
					JOptionPane.showMessageDialog(EmployeeSearchFrame.this, prjReturn.errorMessage);
				}
				
			}
		});
		
		btnDBFill.setFont(new Font("Times New Roman", Font.BOLD, 12));
		btnDBFill.setBounds(307, 19, 68, 23);
		contentPane.add(btnDBFill);
		
		JLabel lblDepartment = new JLabel("Department");
		lblDepartment.setFont(new Font("Times New Roman", Font.BOLD, 12));
		lblDepartment.setBounds(52, 63, 89, 14);
		contentPane.add(lblDepartment);
		
		JLabel lblProject = new JLabel("Project");
		lblProject.setFont(new Font("Times New Roman", Font.BOLD, 12));
		lblProject.setBounds(255, 63, 47, 14);
		contentPane.add(lblProject);
		
		lstProject = new JList<String>(new DefaultListModel<String>());
		lstProject.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lstProject.setModel(project);
		lstProject.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		JScrollPane scrollPaneProject = new JScrollPane(lstProject);
		scrollPaneProject.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPaneProject.setBounds(225, 84, 150, 42);
		contentPane.add(scrollPaneProject);
		
		chckbxNotDept = new JCheckBox("Not");
		chckbxNotDept.setBounds(71, 133, 59, 23);
		contentPane.add(chckbxNotDept);
		
		chckbxNotProject = new JCheckBox("Not");
		chckbxNotProject.setBounds(270, 133, 59, 23);
		contentPane.add(chckbxNotProject);
		
		lstDepartment = new JList<String>(new DefaultListModel<String>());
		lstDepartment.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lstDepartment.setModel(department);
		lstDepartment.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		JScrollPane scrollPaneDepartment = new JScrollPane(lstDepartment);
		scrollPaneDepartment.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPaneDepartment.setBounds(36, 84, 172, 40);
		contentPane.add(scrollPaneDepartment);
		
		
		JLabel lblEmployee = new JLabel("Employee");
		lblEmployee.setFont(new Font("Times New Roman", Font.BOLD, 12));
		lblEmployee.setBounds(52, 179, 89, 14);
		contentPane.add(lblEmployee);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				databaseName = txtDatabase.getText();
				List<String> selectedDepartment = lstDepartment.getSelectedValuesList();
				List<String> selectedProject = lstProject.getSelectedValuesList();
				
				boolean deptNotChecked = chckbxNotDept.isSelected();
				boolean prjNotChecked = chckbxNotProject.isSelected();
				
				textAreaEmployee.setText("");
				
				DatabaseReturn empReturn = FillDatabase.GetEmployees(deptNotChecked, prjNotChecked, selectedDepartment, selectedProject, databaseName);
				if (empReturn.errorMessage.isEmpty()) {
					if (empReturn.data.isEmpty()) {
						textAreaEmployee.setText("No employee found for the set configuration");
					} else {
						for(String employee: empReturn.data){
							textAreaEmployee.append(employee + "\n");
						}
					}
				} else {
					textAreaEmployee.setText("Error: " + empReturn.errorMessage);
					JOptionPane.showMessageDialog(EmployeeSearchFrame.this, empReturn.errorMessage);
				}
			}
		});
		btnSearch.setBounds(80, 276, 89, 23);
		contentPane.add(btnSearch);
		
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textAreaEmployee.setText("");
				lstDepartment.clearSelection();
				lstProject.clearSelection();
				chckbxNotDept.setSelected(false);
				chckbxNotProject.setSelected(false);
			}
		});
		btnClear.setBounds(236, 276, 89, 23);
		contentPane.add(btnClear);
		
		textAreaEmployee = new JTextArea();
		textAreaEmployee.setBounds(36, 197, 339, 68);
		JScrollPane scrollPaneEmployee = new JScrollPane(textAreaEmployee);
		scrollPaneEmployee.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPaneEmployee.setBounds(36, 197, 339, 68);
		contentPane.add(scrollPaneEmployee);
	}
}