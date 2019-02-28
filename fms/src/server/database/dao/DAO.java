package server.database.dao;

import server.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides the base DAO interface for each service.
 * CRUD operations without Update. TODO: add update.
 *
 * @param <T> Model object that will implement the interface.
 */
abstract class DAO<T> {

    protected Connection connection;
    protected String tableName;
    protected String primaryKeyName;

    public DAO() {}
    public DAO(Connection connection, String tableName, String primaryKeyName) {
        this.connection = connection;
        this.tableName = tableName;
        this.primaryKeyName = primaryKeyName;
    }

    abstract T createModel(ResultSet resultSet) throws SQLException;

    abstract boolean create(T model) throws DatabaseException;

    T get(String id) throws DatabaseException {
        String sql = String.format("SELECT * FROM %s WHERE %s=?", tableName, primaryKeyName);
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            // prepare SQL query against SQL-injection.
            statement.setString(1, id);
            // execute the query, build, and return the Person object
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return createModel(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException(
                String.format("Failed to get(id='%s')", id));
        }
        return null; // Person with uuid does not exist
    }

    List<T> getAll() throws DatabaseException {
        List<T> results = new ArrayList<>();
        String sql = "SELECT * FROM " + tableName;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, tableName);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                results.add(createModel(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Failed to getAll()");
        }
        return results;
    }

    boolean delete(String id) throws DatabaseException {
        String sql = "DELETE FROM " + tableName + " WHERE " + primaryKeyName + "=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, id);
            int rows = statement.executeUpdate();
            if (rows == 1) return true;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException(
                String.format("Failed to delete(id='%s')", id));
        }
        return false;
    }


    public void clear() throws DatabaseException {
        String sql = "DELETE FROM " + tableName;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException(
                String.format("Failed to clear(table='%s')", tableName));
        }
    }

}
