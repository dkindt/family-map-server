package server.services;

import server.database.Database;
import server.database.dao.EventDAO;
import server.database.dao.PersonDAO;
import server.database.dao.UserDAO;
import server.database.model.Locations;
import server.database.model.Names;
import server.database.model.Person;
import server.exceptions.DatabaseException;
import shared.request.FillRequest;
import shared.result.FillResult;
import shared.util.generators.FamilyTreeGenerator;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import static java.lang.String.format;

/** Provides service for seeding database with data for a specific User. */
public class FillService {

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

        FillResult result;
        Database db = new Database();
        try (Connection connection = db.openConnection()) {

            eventDAO = new EventDAO(connection);
            personDAO = new PersonDAO(connection);

            Person root = personDAO.getFromUsername(username);
            FamilyTreeGenerator familyTree = new FamilyTreeGenerator();
            familyTree.create(root, generations);

            eventDAO.insertBulk(familyTree.getEvents());
            personDAO.insertBulk(familyTree.getPersons());

            result = new FillResult(format("Created: %s Persons; %s Events",
                familyTree.totalPersons(), familyTree.totalEvents()));

            db.closeConnection(true);

        } catch (DatabaseException | SQLException e) {
            e.printStackTrace();
            db.closeConnection(false);
            result = new FillResult(e.getMessage());
        }
        return result;
    }

}