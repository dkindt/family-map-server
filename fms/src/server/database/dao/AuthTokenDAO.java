package server.database.dao;

import server.database.model.AuthToken;
import server.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthTokenDAO extends DAO<AuthToken> {

    public AuthTokenDAO(Connection connection) {
        super(connection, "auth", "token");
    }

    @Override
    AuthToken createModel(ResultSet resultSet) throws SQLException {
        AuthToken token = new AuthToken();
        token.setToken(resultSet.getString("token"));
        token.setUsername(resultSet.getString("username"));
        return token;
    }

    @Override
    boolean create(AuthToken model) throws DatabaseException {
        return false;
    }
}