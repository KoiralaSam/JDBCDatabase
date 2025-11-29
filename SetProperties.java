import java.io.FileWriter;
import java.util.Properties;

public class SetProperties {
    public static void main(String[] args) {
        Properties properties = new Properties();

        properties.setProperty("DATABASE_URL", "jdbc:mysql://127.0.0.1:3306/");
        properties.setProperty("DATABASE_USERNAME", "root");
        properties.setProperty("DATABASE_PASSWORD", "");
        properties.setProperty("DATABASE_DRIVER", "com.mysql.cj.jdbc.Driver");
        try (
            FileWriter writer = new FileWriter("config.properties")) 
            {
            properties.store(writer, "Database configuration");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}