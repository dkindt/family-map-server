package shared.util.generators;

import server.database.model.Event;
import server.database.model.Person;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static shared.util.DatabaseHelper.generateUUID;

public class EventGenerator {

    private static Random random = new Random();

    public static List<Event> generateEvents(Person mother, Person father, String root, int year) {

        List<Event> events = new ArrayList<>();
        events.add(createBirth(mother, root, year));
        events.add(createBirth(father, root, year));
        events.addAll(createMarriage(mother, father, root, year));

        final int DEATH_THRESHOLD = 1918;
        if (year <= DEATH_THRESHOLD) {
            events.add(createDeath(mother, root, year));
            events.add(createDeath(father,root, year));
        } else {
            events.add(createRandom(mother, root, year));
            events.add(createRandom(father, root, year));
        }

        return events;
    }

    static Event createBirth(Person person, String root, int year) {

        Event birthEvent = LocationGenerator.generate();
        int yob = year - random.nextInt(10);

        birthEvent.setType("Birth");
        birthEvent.setYear(yob);
        birthEvent.setPerson(person.getUUID());
        birthEvent.setDescendant(root);

        return birthEvent;
    }

    private static Event createDeath(Person person, String root, int year) {

        final int currYear = LocalDate.now().getYear();
        final int avgLifeExpectancy = 40;
        final int bound = 50;
        int yod = year + avgLifeExpectancy + random.nextInt(bound);

        Event deathEvent = LocationGenerator.generate();

        if (yod > currYear) yod = 2018;

        deathEvent.setType("Death");
        deathEvent.setPerson(person.getUUID());
        deathEvent.setDescendant(root);
        deathEvent.setYear(yod);

        return deathEvent;
    }

    private static List<Event> createMarriage(Person mother, Person father, String root, int year) {
        Event event = LocationGenerator.generate();
        event.setDescendant(root);
        event.setType("Marriage");
        event.setYear(year);
        event.setPerson(mother.getUUID());

        Event eventCopy = event.clone();
        eventCopy.setPerson(father.getUUID());
        eventCopy.setUUID(generateUUID());

        return new ArrayList<>() {{
            add(event);
            add(eventCopy);
        }};
    }

    private static Event createRandom(Person person, String root, int year) {

        int bound = 10;
        int eventYear = year + bound + random.nextInt(15);

        Event randomEvent = LocationGenerator.generate();
        randomEvent.setType(getRandomType());
        randomEvent.setYear(eventYear);
        randomEvent.setPerson(person.getUUID());
        randomEvent.setDescendant(root);

        return randomEvent;
    }

    private static String getRandomType() {

        String[] types = {
            "Finished CS240",
            "Baptism",
            "Graduated College",
            "Lost Wallet",
            "Found Wallet",
            "Upgraded to Macbook Pro"
        };

        final int idx = random.nextInt(types.length);
        return types[idx];
    }

}
