package shared.result;

abstract class BaseResult {

    private String message;

    BaseResult() {

    }

    BaseResult(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
