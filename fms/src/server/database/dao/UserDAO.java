package server.database.dao;

import server.database.model.User;
import server.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO extends DAO<User> {

    public UserDAO(Connection connection) {
        super(connection, "users", "username");
    }

    @Override
    User modelFromResultSet(ResultSet resultSet) throws SQLException {
        return new User(resultSet);
    }

    @Override
    public boolean create(User user) throws DatabaseException {
        String sql = "INSERT INTO " + tableName + " VALUES (?,?,?,?,?,?,?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getFirstName());
            statement.setString(5, user.getLastName());
            statement.setString(6, user.getGender());
            statement.setString(7, user.getPersonID());
            int rows = statement.executeUpdate();
            if (rows == 1) return true;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Failed to create new User!");
        }
        return false;
    }
}