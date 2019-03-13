package server.exceptions;

import static java.lang.String.format;

public class InvalidParameterException extends Exception {

    private String parameter;

    public InvalidParameterException(String message, String parameter) {
        super(message);
        this.parameter = parameter;
    }

    public String getParameter() {
        return parameter;
    }

    @Override
    public String getMessage() {
        return format("InvalidParameterException(param=\'%s\', msg=\'%s\')",
            super.getMessage(), parameter);
    }
}