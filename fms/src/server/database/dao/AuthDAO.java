package server.database.dao;

import server.database.model.AuthToken;
import server.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static java.lang.String.format;

public class AuthDAO extends DAO<AuthToken> {

    public AuthDAO(Connection connection) {
        super(connection, "auth", "token");
    }

    @Override
    AuthToken modelFactory(ResultSet resultSet) throws SQLException {
        AuthToken token = new AuthToken();
        token.setToken(resultSet.getString("token"));
        token.setUsername(resultSet.getString("username"));
        return token;
    }

    @Override
    public boolean create(AuthToken authToken) throws DatabaseException {
        String sql = format("INSERT INTO %s VALUES (?,?)", tableName);
        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, authToken.getToken());
            statement.setString(2, authToken.getUserName());

            int rows = statement.executeUpdate();
            if (rows == 1) return true;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException(format("Failed to insert %s", authToken.toString()));
        }
        return false;
    }
}