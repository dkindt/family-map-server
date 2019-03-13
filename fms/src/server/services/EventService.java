package server.services;

import server.database.Database;
import server.database.dao.EventDAO;
import server.database.model.Event;
import server.exceptions.AuthenticationException;
import server.exceptions.DatabaseException;
import shared.result.EventResult;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static java.lang.String.format;

/**
 * Provides the service for retrieving a single Event, or, retrieving all the
 * Events for the currently logged-in User.
 */
public class EventService extends BaseService {

    private Database database;
    private EventDAO eventDAO;

    public EventService() {
        this.database = new Database();
    }

    /**
     * Get a single Event matching the eventID.
     *
     * @param id of the Event to retrieve.
     * @return the found SingleEventResult or null.
     */
    public EventResult getEvent(String id, String token)
        throws AuthenticationException, DatabaseException {

        log.entering("EventService", "getEvent");

        try (Connection connection = database.openConnection()) {

            eventDAO = new EventDAO(connection);
            Event event = eventDAO.getFromAuth(id, token);
            if (event == null) {
                log.severe("Person not found");
                throw new DatabaseException(
                    format("Event(id='%s') does NOT exist!", id));
            }
            return new EventResult(new Event[]{event});

        } catch (SQLException e) {
            throw new DatabaseException("Failed to get Person!", e);
        }
    }

    /**
     * Retrieve all of the Event objects from the database
     * for the currently logged-in User.
     *
     * @return MultiEventResult object.
     */
    public EventResult getAllEvents(String token)
        throws AuthenticationException, DatabaseException {

        log.entering("EventService", "getAllEvents");

        try (Connection connection = database.openConnection()) {

            eventDAO = new EventDAO(connection);
            List<Event> events = eventDAO.getAll();
            Event[] data = new Event[events.size()];
            data = events.toArray(data);

            if (data.length != 0) {
                return new EventResult(data);
            }
            return new EventResult("No events found!");

        } catch (SQLException e) {
            throw new DatabaseException("Failed to get all events", e);
        }
    }

}