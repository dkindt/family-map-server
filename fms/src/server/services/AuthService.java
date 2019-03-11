package server.services;

import server.database.Database;
import server.database.dao.AuthDAO;
import server.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.SQLException;

public class AuthService extends BaseService {

    public static boolean verifyAuthentication(String token, String username) {

        boolean authenticated = false;
        Database db = new Database();
        try {
            try (Connection connection = db.openConnection()) {

                AuthDAO authDAO = new AuthDAO(connection);
                authenticated = authDAO.verify(token, username);

            } catch (SQLException e) {
                throw new DatabaseException("Unable to establish connection!", e);
            }
        } catch (DatabaseException e) {
            log.severe(e.getMessage());
        }
        return authenticated;
    }
}
