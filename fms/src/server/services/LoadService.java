package server.services;

import server.database.Database;
import server.database.dao.EventDAO;
import server.database.dao.PersonDAO;
import server.database.dao.UserDAO;
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

        log.entering("LoadService", "load");

        String message;
        LoadResult result = new LoadResult();
        Database db = new Database();

        try {
            request.verify();
            try (Connection connection = db.openConnection()) {

                UserDAO userDAO = new UserDAO(connection);
                PersonDAO personDAO = new PersonDAO(connection);
                EventDAO eventDAO = new EventDAO(connection);

                int users = userDAO.insertBulk(Arrays.asList(request.getUsers()));
                int persons = personDAO.insertBulk(Arrays.asList(request.getPersons()));
                int events = eventDAO.insertBulk(Arrays.asList(request.getEvents()));

                message = format(
                    "Successfully added %d users, %d persons, and %d events",
                    users, persons, events
                );
                connection.commit();

            } catch (SQLException e) {
                throw new DatabaseException(
                    "Unable to establish DB connection!", e);
            }
        } catch (DatabaseException e) {
            log.severe(e.getMessage());
            message = e.getMessage();
        }
        result.setMessage(message);
        return result;
    }
}