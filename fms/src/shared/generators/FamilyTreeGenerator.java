package shared.generators;

import server.database.dao.EventDAO;
import server.database.dao.PersonDAO;
import server.database.model.Event;
import server.database.model.Person;
import server.exceptions.DatabaseException;
import shared.generators.NameGenerator.NameType;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import static shared.generators.EventGenerator.*;
import static shared.generators.NameGenerator.NameType.*;

public class FamilyTreeGenerator extends BaseGenerator {

    final public static int DEFAULT_GENERATIONS = 4;

    private String root;  // User this FamilyTree belongs to.

    // In the end, our FamilyTree is just two lists that will need
    // to be added to our Database.
    private List<Person> persons = new ArrayList<>();
    private int personsAdded;
    private List<Event> events = new ArrayList<>();
    private int eventsAdded;

    // Helpful Generator classes
    private NameGenerator nameGenerator = new NameGenerator();

    public int getPersonsAdded() {
        return personsAdded;
    }

    public int getEventsAdded() {
        return eventsAdded;
    }

    public int save(Connection connection) throws DatabaseException {

        int rows = 0;
        EventDAO eventDAO = new EventDAO(connection);
        PersonDAO personDAO = new PersonDAO(connection);

        log.info("Adding Event objects to Database");
        rows += eventDAO.insertBulk(events);

        log.info("Adding Person objects to Database");
        rows += personDAO.insertBulk(persons);

        return rows;
    }

    public void generateFamilyTree(Person person) {

        generateFamilyTree(person, DEFAULT_GENERATIONS);
    }

    public void generateFamilyTree(Person person, int generations) {

        log.entering("FamilyTreeGenerator", "create", "Generating Family Tree");

        root = person.getDescendant();
        int year = calcBirthYear(EventGenerator.CURRENT_YEAR);

        persons.add(person);
        events.add(EventGenerator.createBirth(person, person.getDescendant(), year));
        events.add(EventGenerator.createRandom(person, year));
        events.add(EventGenerator.createRandom(person, year));

        eventsAdded += 3;
        personsAdded += 1;

        generateHelper(person, generations - 1, year);
    }

    private void generateHelper(Person child, int generations, int year) {

        int yearOfBirth = calcBirthYear(year);

        Person[] parents = generateParents(child);
        Person mother = parents[0];
        Person father = parents[1];

        // insert all of the events for the mom and the dad
        events.addAll(generateEvents(mother, father, root, yearOfBirth));
        eventsAdded += 6;

        // add the parents to our Family Tree
        persons.add(mother);
        persons.add(father);
        personsAdded += 2;

        // recursively call this method until the entire tree is built.
        if (generations != 0) {
            generateHelper(mother, generations - 1, yearOfBirth);
            generateHelper(father, generations - 1, yearOfBirth);
        }
    }

    private Person[] generateParents(Person child) {

        Person mother = generateParent(FEMALE);
        Person father = generateParent(MALE);

        // verify the child's gender, because this will confirm the lastName
        // of the father. In this case, if the child is male, we are assuming
        // that the child shares the same last name and shouldn't use a random one.
        if (child.getGender().equals("m") || child.getSpouse() != null) {
            father.setLastName(child.getLastName());
        }

        // insert a relationship between the parents and the child.
        child.setMother(mother.getUUID());
        child.setFather(father.getUUID());

        // Link the two Person objects using each other's spouseID
        mother.setSpouse(father.getUUID());
        father.setSpouse(mother.getUUID());

        return new Person[]{
            mother,
            father
        };
    }

    private Person generateParent(NameType type) {
        Person parent = new Person();
        parent.setDescendant(root);
        parent.setFirstName(nameGenerator.generateName(type));
        parent.setLastName(nameGenerator.generateName(SURNAME));
        parent.setGender((type.equals(FEMALE)) ? "f" : "m");
        return parent;
    }
}

