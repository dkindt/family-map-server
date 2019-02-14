package shared.result;

import server.database.model.Event;

/** Represents the response message or data returned to the Event service.  */
public class EventResult {

    private Event[] data;
    private String message;

    public EventResult(Event[] data) {}

}
