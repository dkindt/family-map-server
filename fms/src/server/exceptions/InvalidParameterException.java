package server.exceptions;

public class InvalidParameterException extends Exception {

    public InvalidParameterException(String reason) {
        super(reason);
    }

}