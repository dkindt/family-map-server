package server.handlers;

public class AuthenticationException extends Exception {

    enum ErrorCode {
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
