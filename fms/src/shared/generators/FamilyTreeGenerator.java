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
import java.util.Random;

import static shared.generators.EventGenerator.generateEvents;
import static shared.generators.NameGenerator.NameType.*;

public class FamilyTreeGenerator extends BaseGenerator {

    private int DEFAULT_NUM_GENERATIONS = 4;
    private int year = 1999;
    private String root;  // User this FamilyTree belongs to.

    // In the end, our FamilyTree is just two lists that will need
    // to be added to our Database.
    private List<Person> persons = new ArrayList<>();
    private List<Event> events = new ArrayList<>();

    // Helpful Generator classes
    private Random random = new Random();
    private NameGenerator nameGenerator = new NameGenerator();

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

        generateFamilyTree(person, DEFAULT_NUM_GENERATIONS);
    }

    public void generateFamilyTree(Person person, int generations) {

        log.entering("FamilyTreeGenerator", "create", "Generating Family Tree");
        this.root = person.getDescendant();
        persons.add(person);
        events.add(EventGenerator.createBirth(person, person.getDescendant(), year));
        events.add(EventGenerator.createRandom(person, year));
        events.add(EventGenerator.createRandom(person, year));
        generateHelper(person, generations - 1);

    }

    private void generateHelper(Person child, int generations) {

        Person[] parents = generateParents(child);
        Person mother = parents[0];
        Person father = parents[1];

        // insert all of the events for the mom and the dad
        events.addAll(generateEvents(mother, father, root, getYear()));

        // add the parents to our Family Tree
        persons.add(mother);
        persons.add(father);

        // recursively call this method until the entire tree is built.
        if (generations != 0) {
            generateHelper(mother, generations - 1);
            generateHelper(father, generations - 1);
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

    private int getYear() {

        // make the Person's parents older than the current Person!
        final int bound = 10;
        final int gap = 35;
        year = year - gap - random.nextInt(bound);
        return year;
    }
}

