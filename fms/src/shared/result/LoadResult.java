package shared.result;

/** Represents the response message to be returned to the LoadService.  */
public class LoadResult {

    private String message;

    public LoadResult() {}

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
