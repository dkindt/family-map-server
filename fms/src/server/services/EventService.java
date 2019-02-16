package server.services;


import shared.result.MultiEventResult;
import shared.result.SingleEventResult;

/**
 * Provides the service for retrieving a single Event, or, retrieving all the
 * Events for the currently logged-in User.
 */
public class EventService {

    /**
     * Get a single Event matching the eventID.
     *
     * @param eventID to find & retrieve from the database.
     * @return the found SingleEventResult or null.
     */
    public SingleEventResult getEvent(String eventID) {
        return null;
    }

    /**
     * Retrieve all of the Event objects from the database
     * for the currently logged-in User.
     *
     * @return MultiEventResult object.
     */
    public MultiEventResult getAllEvents() {
        return null;
    }

}