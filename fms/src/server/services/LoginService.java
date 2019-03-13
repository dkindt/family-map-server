package server.services;

import server.database.Database;
import server.database.dao.AuthDAO;
import server.database.dao.UserDAO;
import server.database.model.AuthToken;
import server.database.model.User;
import server.exceptions.DatabaseException;
import shared.request.LoginRequest;
import shared.result.LoginResult;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.logging.Level.SEVERE;
import static shared.util.DatabaseHelper.generateUUID;

/** Provides service for logging a User into their account. */
public class LoginService extends BaseService {

    /**
     * Logs a User in and generates a new auth token.
     *
     * @param request object containing User's credentials.
     * @return LoginResult object.
     */
    public LoginResult login(LoginRequest request) throws DatabaseException {

        Database db = new Database();
        try (Connection connection = db.openConnection()) {

            AuthDAO authDAO = new AuthDAO(connection);
            UserDAO userDAO = new UserDAO(connection);

            Map<String, String> params = new LinkedHashMap<>();
            params.put("username", request.getUsername());
            params.put("password", request.getPassword());
            User user = userDAO.get(params);
            if (user == null) {
                return new LoginResult("username/password incorrect!");
            }
            String username = user.getUsername();
            String personID = user.getPersonID();

            AuthToken authToken = new AuthToken();
            String token = generateUUID();
            authToken.setToken(token);
            authToken.setUsername(username);
            authDAO.insert(authToken);

            db.closeConnection(true);
            return new LoginResult(token, username, personID);

        } catch (DatabaseException | SQLException e) {

            log.log(SEVERE, "Failed to login!", e);
            db.closeConnection(false);
        }
        return null;
    }

}