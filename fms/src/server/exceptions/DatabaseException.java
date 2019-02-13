package server.exceptions;

public class DatabaseException extends Exception {

    public DatabaseException(String reason) { super(reason); }
    public DatabaseException() { super(); }

}
