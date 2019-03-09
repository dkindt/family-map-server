package shared.result;

abstract class BaseResult {

    private boolean success;
    private String message;

    BaseResult() {

    }

    BaseResult(String message) {
        this.message = message;
    }

    BaseResult(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
