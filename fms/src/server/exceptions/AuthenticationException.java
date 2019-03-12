package server.exceptions;

public class AuthenticationException extends Exception {

    public AuthenticationException() {
        super("Verify that you are logged in and/or your auth token");
    }
    public AuthenticationException(String reason) {
        super(reason);
    }
}
