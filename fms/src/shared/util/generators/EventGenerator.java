package shared.util.generators;

import server.database.model.Event;
import server.database.model.Person;

import java.util.ArrayList;
import java.util.List;

public class EventGenerator {

    public static Event createBirth(Person person) {

        return new Event();
    }

    public static Event createDeath() {

        return new Event();
    }

    public static List<Event> createMarriage(String descendant, Person mother, Person father, int year) {
        Event event = LocationGenerator.generate();
        event.setDescendant(descendant);
        event.setType("Marriage");
        event.setYear(year);
        event.setPerson(mother.getUUID());

        Event eventCopy = event.clone();
        eventCopy.setPerson(father.getUUID());

        return new ArrayList<>() {{
            add(event);
            add(event.clone());
        }};
    }

    public Event createRandom() {

        return new Event();
    }

}
