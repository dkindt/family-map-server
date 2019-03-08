package shared.result;

import server.database.model.Event;

public class EventResult extends BaseResult {

    Event[] data;

    public EventResult(Event[] data) {
        this.data = data;
    }

    public EventResult(String message) {
        super(message);
    }
}
