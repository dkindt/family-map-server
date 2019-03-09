package server.services;

import server.database.Database;
import server.database.dao.EventDAO;
import server.database.dao.PersonDAO;
import server.database.dao.UserDAO;
import server.database.model.Person;
import server.database.model.User;
import server.exceptions.DatabaseException;
import shared.result.FillResult;
import shared.util.generators.FamilyTreeGenerator;

import java.sql.Connection;
import java.sql.SQLException;

import static java.lang.String.format;

/** Provides service for seeding database with data for a specific User. */
public class FillService extends BaseService {

    /**
     * Fills a User's pedigree from the FillRequest body.
     *
     * @param username
     * @param generations
     * @return FillResult object.
     */
    public FillResult fill(String username, int generations) throws DatabaseException {

        log.entering("FillService", "fill");

        boolean success = false;
        String respMessage;

        Database db = new Database();
        try (Connection connection = db.openConnection()) {

            UserDAO userDAO = new UserDAO(connection);
            userDAO.clearUserData(username);

            User user = userDAO.get(username);
            Person root = Person.fromUser(user);

            FamilyTreeGenerator familyTree = new FamilyTreeGenerator();
            familyTree.create(root, generations);
            int rows = familyTree.save(connection);

            success = true;
            respMessage = format("Family Tree successfully added! %s total rows inserted", rows);

            db.closeConnection(true);

        } catch (SQLException e) {

            throw new DatabaseException("Unable to create Connection to Database.");

        } catch (DatabaseException e) {

            log.severe(e.getMessage());
            db.closeConnection(false);
            respMessage = e.getMessage();

        }
        return new FillResult(respMessage, success);
    }
}