import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class FillDatabase {
    public static DatabaseReturn GetDepartment(String databaseName) {
        
        ArrayList<String> departments = new ArrayList<>();
        DatabaseReturn dbReturn = new DatabaseReturn(departments, "");
        Connection con = DB.GetDB();

        if (con == null) {
            System.err.println("Failed to establish database connection.");
            return dbReturn;
        }

        try {

           

            String query_departments = """
                    SELECT * FROM DEPARTMENT;
                    """;

            try (Statement stmt = con.createStatement()) {
                stmt.execute("CREATE DATABASE IF NOT EXISTS " + databaseName);
                stmt.execute("USE " + databaseName);

                ResultSet result = stmt.executeQuery(query_departments);

                while (result.next()) {
                    String Dname = result.getString("Dname");
                    if(Dname != null){
                        departments.add(Dname);
                    }
                }
            }
            

            con.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            dbReturn.errorMessage = e.getMessage();
        }

        return dbReturn;
    }

    public static DatabaseReturn GetProjects(String databaseName) {
        ArrayList<String> projects = new ArrayList<>();
        DatabaseReturn dbReturn = new DatabaseReturn(projects, "");
        Connection con = DB.GetDB();

        if (con == null) {
            System.err.println("Failed to establish database connection.");
            return dbReturn;
        }

        try {

            try (Statement stmt = con.createStatement()) {
                stmt.execute("CREATE DATABASE IF NOT EXISTS " + databaseName);
                stmt.execute("USE " + databaseName);

                String query_projects = """
                    SELECT * FROM PROJECT;
                    """;

                ResultSet result = stmt.executeQuery(query_projects);

                while (result.next()) {
                    String Pname = result.getString("Pname");

                    if(Pname != null){
                        projects.add(Pname);
                    }
                    
                }
            }
            con.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            dbReturn.errorMessage = e.getMessage();
        }
        return dbReturn;
    }

    public static DatabaseReturn GetEmployees(boolean deptNotChecked, boolean prjNotChecked, List<String> selectedDepartment, List<String> selectedProject, String databaseName) {
        ArrayList<String> employees = new ArrayList<>();
        DatabaseReturn dbReturn = new DatabaseReturn(employees, "");
        Connection con = DB.GetDB();

        if(con == null) {
            System.err.println("Failed to connect to the database");
            return dbReturn;
        }

        try {
            StringBuilder query_employees = new StringBuilder();

            query_employees.append("SELECT E.Fname, E.Lname FROM EMPLOYEE E ");

            //checking to see if the user has selected any departments
            Boolean needsDepartment = !selectedDepartment.isEmpty();
            if(needsDepartment){
                query_employees.append("JOIN DEPARTMENT D ON E.Dno = D.Dnumber ");
            }

            //checking to see if the user has selected any projects
            Boolean needsWorksOn = !selectedProject.isEmpty();
            if(needsWorksOn){
                query_employees.append("JOIN WORKS_ON W ON E.Ssn = W.Essn ");
                query_employees.append("JOIN PROJECT P ON W.Pno = P.Pnumber ");
            }

            //Building the where clauses if the user has selected departments
            ArrayList<String> whereClauses = new ArrayList<>();
            if(needsDepartment){
                StringBuilder departmentWhereClauses = new StringBuilder();
                if(deptNotChecked){
                    departmentWhereClauses.append("D.Dname NOT IN (");
                }
                else{
                    departmentWhereClauses.append("D.Dname IN (");
                }
                for(int i = 0; i < selectedDepartment.size(); i++){
                    if(i >0){
                        departmentWhereClauses.append(",");
                    }
                    departmentWhereClauses.append("'").append(selectedDepartment.get(i)).append("'");
                }
                departmentWhereClauses.append(") ");
                whereClauses.add(departmentWhereClauses.toString());
            }

            // Building the where clauses if the user has selected projects
            if(needsWorksOn){
                StringBuilder projectWhereClauses = new StringBuilder();
                if(prjNotChecked){
                    projectWhereClauses.append("P.Pname NOT IN (");
                }
                else{
                    projectWhereClauses.append("P.Pname IN (");
                }
                for(int i = 0; i < selectedProject.size(); i++){
                    if(i >0){
                        projectWhereClauses.append(",");
                    }
                    projectWhereClauses.append("'").append(selectedProject.get(i)).append("'");
                }
                projectWhereClauses.append(")");
                whereClauses.add(projectWhereClauses.toString());
            }
             
            //Adding thw WHERE and AND in between the where clauses
            if(!whereClauses.isEmpty()){
                query_employees.append("WHERE ");
                for(int i = 0; i < whereClauses.size(); i++){
                    if(i >0){
                        query_employees.append("AND ");
                    }
                    query_employees.append(whereClauses.get(i));
                }
            }
           query_employees.append(" ORDER BY E.Lname, E.Fname");
           String query = query_employees.toString();

            try (Statement stmt = con.createStatement()) {
                stmt.execute("CREATE DATABASE IF NOT EXISTS " + databaseName);
                stmt.execute("USE " + databaseName);

                try (ResultSet result = stmt.executeQuery(query)) {
                    while (result.next()) {
                        String Fname = result.getString("Fname");
                        String Lname = result.getString("Lname");
                        employees.add(Fname + " " + Lname);
                    }
                }
            }
            
            con.close();
            
        } catch (Exception e) {
            System.err.println("Error querying employees: " + e.getMessage());
            e.printStackTrace();
            dbReturn.errorMessage = e.getMessage();
        }
        
        return dbReturn;
        
    }
}

