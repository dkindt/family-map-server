package server.services;

import server.database.Database;
import server.database.dao.EventDAO;
import server.database.dao.PersonDAO;
import server.database.dao.UserDAO;
import server.exceptions.DatabaseException;
import shared.request.LoadRequest;
import shared.result.LoadResult;

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
    public LoadResult load(LoadRequest request) {

        String message;
        LoadResult result = new LoadResult();
        // check to make sure all of the request params were passed in (not null).
        // instantiate the DAOs
        try {
            Database db = new Database();
            try (Connection connection = db.openConnection()) {

                UserDAO userDAO = new UserDAO(connection);
                PersonDAO personDAO = new PersonDAO(connection);
                EventDAO eventDAO = new EventDAO(connection);

                int users = userDAO.insertBulk(Arrays.asList(request.getUsers()));
                int persons = personDAO.insertBulk(Arrays.asList(request.getPersons()));
                int events = eventDAO.insertBulk(Arrays.asList(request.getEvents()));

                String msg = "Successfully added %s users, %s persons, and %s events";
                message = format(msg, users, persons, events);

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