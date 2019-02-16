package shared.result;

import server.database.model.Event;

/** Represents the response message or data returned to the Event service.  */
public class MultiEventResult {

    private Event[] data;
    private String message;

    public MultiEventResult(Event[] data) {}

    public Event[] getData() {
        return data;
    }

    public void setData(Event[] data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
