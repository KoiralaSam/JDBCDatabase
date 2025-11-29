import java.io.FileReader;
import java.sql.*;
import java.util.Properties;

public class DB {
    public static Connection GetDB() {
        Connection con = null;
        try {
            FileReader reader = new FileReader("config.properties");
            Properties p = new Properties(); 
            p.load(reader);

            String dbdriver = p.getProperty("DATABASE_DRIVER");
            String dburl = p.getProperty("DATABASE_URL");
            String dbusername = p.getProperty("DATABASE_USERNAME");
            String dbpassword = p.getProperty("DATABASE_PASSWORD");

            Class.forName(dbdriver);
            
            con = DriverManager.getConnection(dburl, dbusername, dbpassword);
            
            if(!con.isClosed()){
                System.out.println("Successfully connected to PostgreSQL server using TCP/IP...");
            }

            
        } catch (Exception e) {
            System.err.println("Database connection error: " + e.getMessage());
            e.printStackTrace();
        }
        
        return con;
       
    }
}