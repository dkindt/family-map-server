package shared.generators;

import server.database.model.Event;
import server.database.model.Person;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static shared.util.DatabaseHelper.generateUUID;

public class EventGenerator {

    final public static int CURRENT_YEAR = LocalDate.now().getYear();
    final private static int DEFAULT_BOUND = 10;
    final private static int MARRIAGE_AGE = 24;
    final private static int GENERATIONAL_GAP = 30;
    final private static int AVG_LIFE_EXPECTANCY = 72;

    private static Random random = new Random();


    public static int calcBirthYear(int year) {
        return year - GENERATIONAL_GAP;
    }

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

    static Event createBirth(Person person, String root, int yearOfBirth) {

        Event event = LocationGenerator.generate();
        event.setEventType("Birth");
        event.setYear(yearOfBirth);
        event.setPerson(person.getUUID());
        event.setDescendant(root);

        return event;
    }

    private static Event createDeath(Person person, String root, int year) {

        Event deathEvent = LocationGenerator.generate();

        deathEvent.setEventType("Death");
        deathEvent.setPerson(person.getUUID());
        deathEvent.setDescendant(root);
        deathEvent.setYear(calcDeathYear(year));

        return deathEvent;
    }

    private static List<Event> createMarriage(Person mother, Person father, String root, int year) {

        Event event = LocationGenerator.generate();
        event.setDescendant(root);
        event.setEventType("Marriage");
        event.setYear(calcMarriageYear(year));
        event.setPerson(mother.getUUID());

        Event eventCopy = event.clone();
        eventCopy.setPerson(father.getUUID());
        eventCopy.setEventID(generateUUID());

        return new ArrayList<>() {{
            add(event);
            add(eventCopy);
        }};
    }

    public static Event createRandom(Person person, int year) {

        return createRandom(person, person.getDescendant(), year);
    }

    private static Event createRandom(Person person, String root, int year) {

        int yearOfEvent = calcRandomYear(year, DEFAULT_BOUND);
        Event event = LocationGenerator.generate();
        event.setEventType(getRandomType());
        event.setYear(yearOfEvent);
        event.setPerson(person.getUUID());
        event.setDescendant(root);

        return event;
    }

    private static String getRandomType() {

        String[] types = {
            "Finished CS240",
            "Baptism",
            "Graduated College",
            "Lost Wallet",
            "Found Wallet",
            "Upgraded to MacBook Pro"
        };

        final int idx = random.nextInt(types.length);
        return types[idx];
    }

    private static int calcRandomYear(int year, int bound) {

        int x = random.nextInt();
        int y = random.nextInt();
        return year + bound * (x / y);
    }

    private static int calcDeathYear(int yearOfBirth) {

        int yod = yearOfBirth + AVG_LIFE_EXPECTANCY;
        return (yod > CURRENT_YEAR) ? CURRENT_YEAR : yod;
    }

    private static int calcMarriageYear(int year) {

        final int bound = 10;
        return (year + MARRIAGE_AGE + bound * ((random.nextInt() + bound) % bound));
    }

}
