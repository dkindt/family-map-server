package shared.result;

import server.database.model.Event;

/** Represents the response message or data returned to the Event service.  */
public class SingleEventResult {

    private Event event;
    private String message;

    public SingleEventResult(Event event, String message) {
        this.event = event;
        this.message = message;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
