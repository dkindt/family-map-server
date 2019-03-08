package server.database.dao;

import server.database.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO extends DAO<User> {

    public UserDAO(Connection connection) {
        super(connection, "users", "username");
    }

    @Override
    User modelFactory(ResultSet resultSet) throws SQLException {

        User user = new User();
        user.setUsername(resultSet.getString("username"));
        user.setPassword(resultSet.getString("password"));
        user.setEmail(resultSet.getString("email"));
        user.setFirstName(resultSet.getString("first_name"));
        user.setLastName(resultSet.getString("last_name"));
        user.setGender(resultSet.getString("gender"));
        user.setPersonID(resultSet.getString("person_id"));
        return user;
    }

    @Override
    int getNumColumns() {
        return User.class.getDeclaredFields().length;
    }

    @Override
    void bindParameters(PreparedStatement statement, User user) throws SQLException {

        statement.setString(1, user.getUsername());
        statement.setString(2, user.getPassword());
        statement.setString(3, user.getEmail());
        statement.setString(4, user.getFirstName());
        statement.setString(5, user.getLastName());
        statement.setString(6, user.getGender());
        statement.setString(7, user.getPersonID());
    }
}