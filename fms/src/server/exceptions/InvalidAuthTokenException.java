package server.exceptions;

public class InvalidAuthTokenException extends Exception {

    public InvalidAuthTokenException(String reason) {
        super(reason);
    }

}
