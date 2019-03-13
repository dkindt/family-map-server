package server.services;

import server.database.Database;
import server.database.dao.UserDAO;
import server.database.model.Person;
import server.database.model.User;
import server.exceptions.DatabaseException;
import shared.result.FillResult;
import shared.generators.FamilyTreeGenerator;

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

        String respMessage;

        Database db = new Database();
        try (Connection connection = db.openConnection()) {

            UserDAO userDAO = new UserDAO(connection);
            userDAO.clearUserData(username);

            User user = userDAO.get(username);
            Person root = Person.fromUser(user);

            FamilyTreeGenerator familyTree = new FamilyTreeGenerator();
            familyTree.generateFamilyTree(root, generations);
            familyTree.save(connection);

            respMessage = format("Successfully added %s persons and %s events",
                familyTree.getPersonsAdded(), familyTree.getEventsAdded());

            db.closeConnection(true);

        } catch (SQLException e) {

            throw new DatabaseException("Unable to create Connection to Database.");

        } catch (DatabaseException e) {

            log.severe(e.getMessage());
            db.closeConnection(false);
            respMessage = e.getMessage();

        }
        return new FillResult(respMessage);
    }
}