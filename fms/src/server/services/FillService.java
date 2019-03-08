package server.services;

import server.database.Database;
import server.database.dao.EventDAO;
import server.database.dao.PersonDAO;
import server.database.model.Person;
import server.exceptions.DatabaseException;
import shared.result.FillResult;
import shared.util.generators.FamilyTreeGenerator;

import java.sql.Connection;
import java.sql.SQLException;

import static java.lang.String.format;

/** Provides service for seeding database with data for a specific User. */
public class FillService extends BaseService {

    PersonDAO personDAO;
    EventDAO eventDAO;

    /**
     * Fills a User's pedigree from the FillRequest body.
     *
     * @param username
     * @param generations
     * @return FillResult object.
     */
    public FillResult fill(String username, int generations) throws DatabaseException {

        int eventsAdded;
        int personsAdded;
        String respMessage;
        Database db = new Database();
        try (Connection connection = db.openConnection()) {

            eventDAO = new EventDAO(connection);
            personDAO = new PersonDAO(connection);

            Person root = personDAO.getFromUsername(username);
            FamilyTreeGenerator familyTree = new FamilyTreeGenerator();
            familyTree.create(root, generations);

            eventsAdded = eventDAO.insertBulk(familyTree.getEvents());
            personsAdded = personDAO.insertBulk(familyTree.getPersons());
            respMessage = format("%s Events added; %s Persons added", eventsAdded, personsAdded);

            db.closeConnection(true);

        } catch (DatabaseException | SQLException e) {

            log.severe("DatabaseException occurred!" + e);
            db.closeConnection(false);
            respMessage = e.getMessage();
        }
        return new FillResult(respMessage);
    }

}