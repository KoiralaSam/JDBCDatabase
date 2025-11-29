import java.util.List;

public class DatabaseReturn {
    public List<String> results;
    public String errorMessage;

    public DatabaseReturn(List<String> results, String errorMessage) {
        this.results = results;
        this.errorMessage = errorMessage;
    }

    public List<String> getResults() {
        return results;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
