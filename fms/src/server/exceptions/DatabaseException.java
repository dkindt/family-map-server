package server.exceptions;

public class DatabaseException extends Exception {

    public DatabaseException() { super(); }
    public DatabaseException(String reason) { super(reason); }
    public DatabaseException(String reason, Throwable cause) {
        super(reason, cause);
    }

}
