import java.io.FileWriter;
import java.util.Properties;

public class SetProperties {
    public static void main(String[] args) {
        Properties properties = new Properties();

        properties.setProperty("DATABASE_URL", "mysql -h ulm-csci-lonsmith2.ccr8ibhqw8qf.us-east-2.rds.amazonaws.com -P 3306 -p");
        properties.setProperty("DATABASE_USERNAME", "");
        properties.setProperty("DATABASE_PASSWORD", "");

        try {
            FileWriter writer = new FileWriter("config.properties");
            properties.store( writer, "Database configuration");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
