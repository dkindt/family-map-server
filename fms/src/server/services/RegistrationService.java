package server.services;

import server.database.Database;
import server.database.dao.AuthDAO;
import server.database.dao.PersonDAO;
import server.database.dao.UserDAO;
import server.database.model.AuthToken;
import server.database.model.Person;
import server.database.model.User;
import server.exceptions.DatabaseException;
import shared.request.RegistrationRequest;
import shared.result.RegistrationResult;

import java.sql.Connection;
import java.sql.SQLException;

import static shared.util.DatabaseHelper.generateUUID;

/** Provides service for registering a new User. */
public class RegistrationService extends BaseService {

    /**
     * Creates a new user account, generates 4 generations of ancestor data for
     * the new user, logs the user in, and returns an auth token.
     *
     * @param request RegistrationRequest object
     * @return RegistrationResult (response) object.
     */
    public RegistrationResult register(RegistrationRequest request) {

        log.entering("RegistrationService", "register");

        Database db = new Database();
        boolean commit = false;
        try (Connection connection = db.openConnection()) {

            UserDAO userDAO = new UserDAO(connection);
            User user = userDAO.get(request.getUsername());

            if (user == null) {

                AuthDAO authDAO = new AuthDAO(connection);
                PersonDAO personDAO = new PersonDAO(connection);

                // create the Person object that will be tied to the new
                // User and insert it into the database.
                Person person = new Person();
                person.setDescendant(request.getUsername());
                person.setFirstName(request.getFirstName());
                person.setLastName(request.getLastName());
                person.setGender(request.getGender());
                personDAO.create(person);

                // create the User object and insert into the database.
                user = new User();
                user.setUsername(request.getUsername());
                user.setPassword(request.getPassword());
                user.setEmail(request.getEmail());
                user.setFirstName(request.getFirstName());
                user.setLastName(request.getLastName());
                user.setGender(request.getGender());
                user.setPersonID(person.getUUID());
                userDAO.create(user);

                // 'login' the User and generate a new AuthToken for them.
                AuthToken authToken = new AuthToken();
                String token = generateUUID();
                authToken.setToken(token);
                authToken.setUsername(user.getUsername());
                authDAO.create(authToken);

                db.closeConnection(true);
                return new RegistrationResult(token, user.getUsername(), person.getUUID());

            } else {

                // username already taken
                return new RegistrationResult("username already taken!");
            }

        } catch (DatabaseException | SQLException e) {

            return new RegistrationResult(e.getMessage());

        }
        //return null;
    }

}