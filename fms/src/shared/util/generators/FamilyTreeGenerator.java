package shared.util.generators;

import server.database.model.Event;
import server.database.model.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FamilyTreeGenerator {

    private String root;  // User this FamilyTree belongs to.
    private Random random = new Random();

    private List<Person> persons;
    private List<Event> events;

    public FamilyTreeGenerator(Person root) {

        this.root = root.getDescendant();
        this.persons = new ArrayList<>() {{
            add(root);
        }};
    }

    public void generateTree(Person child, int generations, int year) {

        // make the Person's parents older than the current Person!
        // also, ensure that Event's aren't
        year = generateRandomYear(year);

        Person[] parents = generateParents();
        Person mother = parents[0];
        Person father = parents[1];

        // create a relationship between the parents and the child.
        child.setMother(mother.getUUID());
        child.setFather(father.getUUID());

        // create all of the events for the mom and the dad:
        //  (1) Birth
        //  (2) Marriage
        //  (3) Random Event (Death, etc.)
        createEvents(mother, father, year);

        // recursively call this method until the entire tree is built.
        if (generations != 0) {
            generateTree(mother, generations - 1, year);
            generateTree(father, generations - 1, year);
        }

    }

    private void createEvents(Person mother, Person father, int year) {

        events.add(EventGenerator.createBirth(mother));
        events.add(EventGenerator.createBirth(father));
        events.addAll(EventGenerator.createMarriage(root, mother, father, year));
    }

    private int generateRandomYear(int year) {

        final int bound = 10;
        final int gap = 35;
        return year - gap - random.nextInt(bound);
    }

    private Person[] generateParents() {

        Person mother = new Person();
        Person father = new Person();

        mother.setSpouse(father.getUUID());
        father.setSpouse(mother.getUUID());

        return new Person[]{
            mother,
            father
        };
    }
}

