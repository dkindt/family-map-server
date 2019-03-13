package server.exceptions;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.net.URLEncoder;
import java.sql.SQLException;

import static java.lang.String.format;


public class DatabaseException extends Exception {

    private boolean causedBySQL;
    private String sqlState;
    private int sqlErrorCode;
    private String sqlMessage;

    public DatabaseException() { super(); }
    public DatabaseException(String reason) { super(reason); }
    public DatabaseException(String reason, SQLException cause) {

        super(reason, cause);
        this.sqlState = cause.getSQLState();
        this.sqlErrorCode = cause.getErrorCode();
        this.sqlMessage = cause.getMessage();
        this.causedBySQL = true;
    }

    public String getSqlState() {
        return sqlState;
    }

    public int getSqlErrorCode() {
        return sqlErrorCode;
    }

    public String getSqlMessage() {
        return sqlMessage;
    }

    @Override
    public String getMessage() {
        
        String message = super.getMessage();
        if (causedBySQL) {
            message = format(
                "%s caused by SQLException(state=\'%s\', code=%s message=\'%s\')",
                message, sqlState, sqlErrorCode, sqlMessage
            );
        }
        return message;
    }
}
