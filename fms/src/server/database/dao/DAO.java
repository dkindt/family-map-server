package server.database.dao;

import server.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static java.lang.String.format;

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

    abstract T modelFactory(ResultSet resultSet) throws SQLException;

    abstract public boolean create(T model) throws DatabaseException;

    public T get(Map<String, String> fields) throws DatabaseException {

        String sql = format(
          "SELECT * FROM %s WHERE %s", tableName,
          String.join(" AND ",
              fields.keySet()
                .stream()
                .map(s -> format("%s=?", s))
                .toArray(String[]::new))
        );
        System.out.println(sql);
        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            // prepare SQL query against SQL-injection.
            int paramIdx = 1;
            for (String value : fields.values()) {
                System.out.println(value);
                statement.setObject(paramIdx++, value);
            }
            // execute the query, build, and return the Person object
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                System.out.println("result set exists");
                return modelFactory(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Failed to get() with params!");
        }
        return null;
    }

    public T get(String id) throws DatabaseException {

        String sql = format("SELECT * FROM %s WHERE %s=?", tableName, primaryKeyName);
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            // prepare SQL query against SQL-injection.
            statement.setString(1, id);
            // execute the query, build, and return the Person object
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return modelFactory(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException(
                format("Failed to get(id='%s')", id));
        }
        return null; // Person with uuid does not exist
    }

    public List<T> getAll() throws DatabaseException {

        List<T> results = new ArrayList<>();
        String sql = format("SELECT * FROM %s", tableName);
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, tableName);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                results.add(modelFactory(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Failed to getAll()");
        }
        return results;
    }

    public boolean delete(String id) throws DatabaseException {

        String sql = "DELETE FROM " + tableName + " WHERE " + primaryKeyName + "=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, id);
            int rows = statement.executeUpdate();
            if (rows == 1) return true;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException(
                format("Failed to delete(id='%s')", id));
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
                format("Failed to clear(table='%s')", tableName));
        }
    }

}
