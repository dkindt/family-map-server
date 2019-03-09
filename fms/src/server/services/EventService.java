package server.services;

import server.database.model.Event;

/**
 * Provides the service for retrieving a single Event, or, retrieving all the
 * Events for the currently logged-in User.
 */
public class EventService {

    /**
     * Get a single Event matching the eventID.
     *
     * @param eventID of the Event to retrieve.
     * @return the found SingleEventResult or null.
     */
    public Event[] getEvent(String eventID) {
        return null;
    }

    /**
     * Retrieve all of the Event objects from the database
     * for the currently logged-in User.
     *
     * @return MultiEventResult object.
     */
    public Event[] getAllEvents() {
        return null;
    }

}