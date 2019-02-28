package server.services;

import server.database.Database;
import server.database.dao.UserDAO;
import server.database.model.User;
import server.exceptions.DatabaseException;
import shared.request.LoginRequest;
import shared.result.LoginResult;

import java.sql.Connection;

/** Provides service for logging a User into their account. */
public class LoginService {

    /**
     * Logs a User in and generates a new auth token.
     *
     * @param loginRequest object containing User's credentials.
     * @return LoginResult object.
     */
    public LoginResult login(LoginRequest loginRequest) {

        Database db = new Database();
        try {

            Connection connection = db.openConnection();
            UserDAO userDAO = new UserDAO(connection);
            String username = loginRequest.getUsername();
            String password = loginRequest.getPassword();
            User user = new User();

        } catch (DatabaseException e) {

        }

        return null;
    }

}