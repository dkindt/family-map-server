package server.services;

import org.sqlite.date.ExceptionUtils;
import server.database.Database;
import server.database.dao.EventDAO;
import server.database.dao.PersonDAO;
import server.database.dao.UserDAO;
import server.database.model.Event;
import server.database.model.Person;
import server.database.model.User;
import server.exceptions.DatabaseException;
import server.exceptions.InvalidParameterException;
import shared.request.LoadRequest;
import shared.result.LoadResult;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;

import static java.lang.String.format;
import static java.util.logging.Level.SEVERE;

/** Provides service for clearing and loading custom data into the database. */
public class LoadService extends BaseService {

    /**
     * Clears all data from the database (just like the `/clear` API), and then
     * loads the posted user, person, and event data into the database.
     *
     * @param request payload containing lists of user, person, and event objects.
     * @return LoadResult object with message success/error message(s).
     */
    public LoadResult load(LoadRequest request) throws InvalidParameterException {

        String message;
        LoadResult result = new LoadResult();
        Database db = new Database();

        try {

            // verify that the request parameters are set and valid.
            try {
                request.verify();
            } catch (Exception e) {
                log.log(SEVERE, e.getMessage(), e);
            }

            try (Connection connection = db.openConnection()) {

                UserDAO userDAO = new UserDAO(connection);
                PersonDAO personDAO = new PersonDAO(connection);
                EventDAO eventDAO = new EventDAO(connection);

                // clear out existing data
                log.info("Clearing out existing data...");
                userDAO.clear();
                personDAO.clear();
                eventDAO.clear();

                User[] users = request.getUsers();
                Person[] persons = request.getPersons();
                Event[] events = request.getEvents();
                log.info(Arrays.toString(persons));

                log.fine(format("Loading %s users, %s persons, and %s events",
                    users.length, persons.length, events.length));
                userDAO.insertBulk(Arrays.asList(users));
                personDAO.insertBulk(Arrays.asList(persons));
                eventDAO.insertBulk(Arrays.asList(events));

                message = format(
                    "Successfully added %s users, %s persons, and %s events",
                    users.length, persons.length, events.length
                );
                connection.commit();

            } catch (SQLException e) {
                throw new DatabaseException(
                    "Unable to establish DB connection!", e);
            }
        } catch (DatabaseException e) {

            log.log(SEVERE, "Failed to load database!", e);
            message = e.getMessage();
        }
        result.setMessage(message);
        return result;
    }
}