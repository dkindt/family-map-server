package server.services;

import server.database.Database;
import server.database.dao.AuthDAO;
import server.database.dao.UserDAO;
import server.database.model.AuthToken;
import server.database.model.Person;
import server.database.model.User;
import server.exceptions.DatabaseException;
import shared.request.RegistrationRequest;
import shared.result.RegistrationResult;
import shared.generators.FamilyTreeGenerator;

import java.sql.Connection;
import java.sql.SQLException;

import static java.util.logging.Level.SEVERE;
import static shared.util.DatabaseHelper.generateUUID;


/** Provides service for registering a new User. */
public class RegistrationService extends BaseService {

    public RegistrationService() {

    }

    /**
     * Creates a new user account, generates 4 generations of ancestor data for
     * the new user, logs the user in, and returns an auth token.
     *
     * @param request RegistrationRequest object
     * @return RegistrationResult (response) object.
     */
    public RegistrationResult register(RegistrationRequest request) throws DatabaseException {

        log.info("Registering new User: " + request.getUsername());

        RegistrationResult result;
        Database db = new Database();
        try (Connection connection = db.openConnection()) {

            log.info("Attempting connection to Database");
            UserDAO userDAO = new UserDAO(connection);
            User user = userDAO.get(request.getUsername());

            if (user == null) {

                log.info("Registering new User");
                AuthDAO authDAO = new AuthDAO(connection);

                // insert the Person object that will be tied to the new
                // User and insert it into the WHEN we generate the Family Tree.
                Person person = new Person();
                person.setDescendant(request.getUsername());
                person.setFirstName(request.getFirstName());
                person.setLastName(request.getLastName());
                person.setGender(request.getGender());

                // insert the User object and insert into the database.
                user = new User();
                user.setUsername(request.getUsername());
                user.setPassword(request.getPassword());
                user.setEmail(request.getEmail());
                user.setFirstName(request.getFirstName());
                user.setLastName(request.getLastName());
                user.setGender(request.getGender());
                user.setPersonID(person.getUUID());
                userDAO.insert(user);

                log.info("Building Family Tree for User");
                FamilyTreeGenerator familyTree = new FamilyTreeGenerator();
                familyTree.generateFamilyTree(person);
                familyTree.save(connection);

                // 'login' the User and generate a new AuthToken for them.
                log.info("Generating AuthToken for User");
                AuthToken authToken = new AuthToken();
                String token = generateUUID();
                authToken.setToken(token);
                authToken.setUsername(user.getUsername());
                authDAO.insert(authToken);

                db.closeConnection(true);
                result = new RegistrationResult(token, user.getUsername(), person.getUUID());

            } else {
                log.info("Username already taken");
                result = new RegistrationResult("username already taken!");
            }

        } catch (DatabaseException | SQLException e) {

            log.log(SEVERE, "Failed to register new User!", e);
            db.closeConnection(false);
            result = new RegistrationResult(e.getMessage());
        }
        return result;
    }

}