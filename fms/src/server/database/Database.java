package server.database;

import server.database.dao.AuthDAO;
import server.database.dao.EventDAO;
import server.database.dao.PersonDAO;
import server.database.dao.UserDAO;
import server.exceptions.DatabaseException;

import java.io.IOException;
import java.sql.*;

import static shared.util.FileHelper.loadFile;

public class Database {

    private Connection connection;

    static {
        try {
            //This is how we set up the driver for our database
            final String driver = "org.sqlite.JDBC";
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Connection openConnection() throws DatabaseException {
        final String DB_URI = "jdbc:sqlite:family-map.db";
        try {
            connection = DriverManager.getConnection(DB_URI);
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Failed to establish connection to database!");
        }
        return connection;
    }

    public void closeConnection(boolean commit) throws DatabaseException {

        try {
            if (commit) connection.commit();
            else connection.rollback();
            connection.close();
            connection = null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Failed to close database connection!");
        }
    }

    public void init(String sqlFile) throws DatabaseException, IOException {

        openConnection();
        try (Statement statement = connection.createStatement()) {
            String sql = loadFile(sqlFile);
            statement.executeUpdate(sql);
            closeConnection(true);
        } catch (DatabaseException e) {
            closeConnection(false);
            throw e;
        } catch (SQLException e) {
            closeConnection(false);
            throw new DatabaseException("SQL encountered during createTables()");
        }
    }

    public int clear() throws DatabaseException {

        openConnection();

        AuthDAO authDAO = new AuthDAO(connection);
        EventDAO eventDAO = new EventDAO(connection);
        UserDAO userDAO = new UserDAO(connection);
        PersonDAO personDAO = new PersonDAO(connection);

        int rowsDeleted = 0;
        try {
            rowsDeleted += userDAO.clear();
            rowsDeleted += personDAO.clear();
            rowsDeleted += eventDAO.clear();
            rowsDeleted += authDAO.clear();
            closeConnection(true);

        } catch (DatabaseException e) {
            closeConnection(false);
            throw e;
        }
        return rowsDeleted;
    }
}
