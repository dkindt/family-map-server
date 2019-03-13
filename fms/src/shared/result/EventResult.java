package shared.result;

import server.database.model.Event;

public class EventResult extends BaseResult {

    Event[] data;

    public EventResult() {
        super();
    }

    public EventResult(String message) {
        super(message);
    }

    public EventResult(Event[] data) {
        this.data = data;
    }

    public void setData(Event[] data) {
        this.data = data;
    }

    public Event[] getData() {
        return this.data;
    }
}
