package server.exceptions;

public class InternalServerErrorException extends Exception {

    public InternalServerErrorException(String reason) {
        super(reason);
    }

}