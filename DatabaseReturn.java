import java.util.ArrayList;

public class DatabaseReturn {
    public ArrayList<String> data;
    public String errorMessage;

    public DatabaseReturn(ArrayList<String> data, String errorMessage) {
        this.data = data;
        this.errorMessage = errorMessage;
    }
}
