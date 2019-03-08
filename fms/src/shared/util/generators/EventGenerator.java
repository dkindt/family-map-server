package shared.util.generators;

import server.database.model.Event;
import server.database.model.Person;

import java.util.ArrayList;
import java.util.List;

public class EventGenerator {

    public static List<Event> generateEvents(Person mother, Person father, String root, int year) {

        List<Event> events = new ArrayList<>();
        events.add(createBirth(mother, year));
        events.add(createBirth(father, year));
        events.addAll(createMarriage(mother, father, root, year));
        return events;
    }

    public static Event createBirth(Person person, int year) {

        return new Event();
    }

    public static Event createDeath(int year) {

        return new Event();
    }

    public static List<Event> createMarriage(Person mother, Person father, String root, int year) {
        Event event = LocationGenerator.generate();
        event.setDescendant(root);
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

    private static Event createRandom() {

        return new Event();
    }

}
