package shared.util.generators;

import server.database.model.Event;

public class LocationGenerator {

    public static Event generate() {
        return new Event();
    }

}
