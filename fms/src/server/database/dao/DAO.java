package server.database.dao;

import server.exceptions.DatabaseException;

import java.sql.*;
import java.util.*;
import java.util.logging.Logger;

import static java.lang.String.format;

/**
 * Provides the base DAO interface for each service.
 * CRUD operations without Update. TODO: add update.
 *
 * @param <T> Model object that will implement the interface.
 */
abstract class DAO<T> {

    protected Logger log = Logger.getLogger("family-map-server");

    // Generic SQL query statements
    protected String SQL_DELETE = "DELETE FROM %s WHERE %s=?";
    protected String SQL_DELETE_ROWS = "DELETE FROM %s";
    protected String SQL_INSERT = "INSERT INTO %s VALUES (%s)";
    protected String SQL_SELECT = "SELECT * FROM %s WHERE %s=?";
    protected String SQL_SELECT_ALL = "SELECT * FROM %s";

    protected Connection connection;
    protected String tableName;
    protected String primaryKeyName;

    public DAO() {}
    public DAO(Connection connection, String tableName, String primaryKeyName) {

        this.connection = connection;
        this.tableName = tableName;
        this.primaryKeyName = primaryKeyName;

    }

    private String genPlaceHolderString() {

        String[] chars = new String[getNumColumns()];
        Arrays.fill(chars, "?");
        return String.join(",", chars);
    }

    abstract T modelFactory(ResultSet resultSet) throws SQLException;

    abstract int getNumColumns();

    //abstract public boolean insert(T model) throws DatabaseException;

    abstract void bindParameters(PreparedStatement statement, T model) throws SQLException;

    public boolean insertBulk(List<T> items) throws DatabaseException {

        String sql = format(SQL_INSERT, tableName, genPlaceHolderString());
        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            for (T item : items) {
                bindParameters(statement, item);
                statement.addBatch();
            }

            int[] rows = statement.executeBatch();
            if (rows.length > 0) return true;

        } catch (SQLException e) {

            e.printStackTrace();
            throw new DatabaseException(
                format("Failed to insertBulk() %s items", items.size()));
        }
        return false;
    }

    public boolean insert(T model) throws DatabaseException {

        String sql = format(SQL_INSERT, tableName, genPlaceHolderString());
        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            bindParameters(statement, model);
            int rows = statement.executeUpdate();
            if (rows == 1) return true;

        } catch (SQLException e) {

            e.printStackTrace();
            throw new DatabaseException(
                format("Failed to insert(): %s", model.toString()));
        }
        return false;
    }

    public T get(Map<String, String> fields) throws DatabaseException {

        String sql = format(
          "SELECT * FROM %s WHERE %s", tableName,
          String.join(" AND ",
              fields.keySet()
                .stream()
                .map(s -> format("%s=?", s))
                .toArray(String[]::new))
        );

        log.info(format("PreparedStatement='%s'", sql));

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            // prepare SQL query against SQL-injection.
            int paramIdx = 1;
            for (String value : fields.values()) {
                statement.setObject(paramIdx++, value);
            }
            // execute the query, build, and return the Person object
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return modelFactory(resultSet);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Failed to get() with params!");
        }
        return null;
    }

    public T get(String pk) throws DatabaseException {

        String sql = format(SQL_SELECT, tableName, primaryKeyName);
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            // prepare SQL query against SQL-injection.
            statement.setString(1, pk);
            // execute the query, build, and return the Person object
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return modelFactory(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException(
                format("Failed to get(pk='%s')", pk));
        }
        return null; // Person with uuid does not exist
    }

    public List<T> getAll() throws DatabaseException {

        List<T> results = new ArrayList<>();
        String sql = format(SQL_SELECT_ALL, tableName);
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

        String sql = format(SQL_DELETE, tableName, primaryKeyName);
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


    public int clear() throws DatabaseException {

        int rows;
        final String sql = format(SQL_DELETE, tableName);
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            rows = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException(
                format("Failed to clear(table='%s')", tableName));
        }
        return rows;
    }

}
