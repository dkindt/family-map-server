package server.exceptions;

public class InvalidParameterException extends Exception {

    private String parameter;

    public InvalidParameterException(String message, String parameter) {
        super(message);
        this.parameter = parameter;
    }

    public String getParameter() {
        return parameter;
    }
}