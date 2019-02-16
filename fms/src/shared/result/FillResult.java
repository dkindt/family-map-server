package shared.result;

/** Represents the response message to be returned to the FillService.  */
public class FillResult {

    private String message;

    public FillResult(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
