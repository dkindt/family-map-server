package shared.result;

/** Represents the response message to be returned to the FillService.  */
public class FillResult extends BaseResult {

    public FillResult(String message) {
        super(message);
    }

    public FillResult(String message, boolean success) {
        super(message, success);
    }
}
