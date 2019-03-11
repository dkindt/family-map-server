package server.exceptions;

public class AuthenticationException extends Exception {

    public enum ErrorCode {
        InvalidAuthToken,
        MissingAuthToken
    }

    private ErrorCode code;

    public AuthenticationException(String reason, ErrorCode code) {
        super(reason);
        this.code = code;
    }

    public AuthenticationException(ErrorCode code) {
        super();
        this.code = code;
    }
}
