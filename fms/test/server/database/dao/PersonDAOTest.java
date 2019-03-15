package server.database.dao;

import org.junit.*;
import server.database.Database;
import server.database.model.Person;
import server.exceptions.DatabaseException;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static shared.util.DatabaseHelper.generateUUID;
import static shared.util.FileHelper.getAbsolutePath;

public class PersonDAOTest {

    private static Database db;
    private Person person;

    @BeforeClass
    public static void setupDatabase() throws DatabaseException, IOException {

        final String path = getAbsolutePath("sql/db_init.sql");
        db = new Database();
        db.init(path);
    }

    @Before
    public void setUp() {

        person = new Person();
        person.setUUID("personID_dkindt");
        person.setDescendant("usesrname_dkindt");
        person.setFirstName("Dan");
        person.setLastName("Kindt");
        person.setGender("M");
        person.setFather("dkindt_father");
        person.setMother("dkindt_mother");
        person.setSpouse("dkindt-spouse");
    }

    @After
    public void tearDown() throws DatabaseException {
        db.clear();
    }

    @Test
    public void insertPositive() throws DatabaseException {

        try (Connection connection = db.openConnection()) {

            PersonDAO personDAO = new PersonDAO(connection);

            // Test for inserting a single record into the database.
            boolean inserted = personDAO.insert(person);
            assertTrue(inserted);

            // Test for bulk inserting multiple records into the database.
            Person person1 = person.clone();
            Person person2 = person.clone();
            Person person3 = person.clone();
            person1.setUUID(generateUUID());
            person2.setUUID(generateUUID());
            person3.setUUID(generateUUID());
            Person[] people = new Person[]{
                person1,
                person2,
                person3
            };
            int expected = 3;
            int actual = personDAO.insertBulk(Arrays.asList(people));
            assertEquals(expected, actual);

        } catch (SQLException e) {
            throw new DatabaseException("Failed in createPositive", e);
        }
    }

    @Test
    public void insertNegative() throws SQLException {

        try (Connection connection = db.openConnection()) {

            PersonDAO personDAO = new PersonDAO(connection);
            personDAO.insert(person);
            personDAO.insert(person);

        } catch(DatabaseException e) {

            final String check = "UNIQUE constraint failed: persons.personID";
            assertTrue(e.getMessage().contains(check));
        }
    }

    @Test
    public void getPositive() throws DatabaseException {

        String actualID;
        String expectedID = person.getUUID();
        try (Connection connection = db.openConnection()) {

            PersonDAO personDAO = new PersonDAO(connection);
            personDAO.insert(person);
            Person actual = personDAO.get(expectedID);
            actualID = actual.getUUID();

        } catch (SQLException e) {
            throw new DatabaseException("Failed getPositive", e);
        }
        assertEquals(expectedID, actualID);
    }

    @Test
    public void getNegative() throws DatabaseException {

        try (Connection connection = db.openConnection()) {

            PersonDAO personDAO = new PersonDAO(connection);
            Person person = personDAO.get("INVALID_PERSON_ID");
            assertNull(person);

        } catch (SQLException e) {
            throw new DatabaseException("Failed in getNegative", e);
        }
    }

    @Test
    public void getAll() throws DatabaseException, SQLException {

        try (Connection connection = db.openConnection()) {

            PersonDAO personDAO = new PersonDAO(connection);
            Person person1 = person.clone();
            person1.setUUID(generateUUID());
            Person person2 = person.clone();
            person2.setUUID(generateUUID());
            Person person3 = person.clone();
            person3.setUUID(generateUUID());
            Person person4= person.clone();
            person4.setUUID(generateUUID());
            Person[] people = new Person[]{
                person,
                person1,
                person2,
                person3,
                person4
            };
            personDAO.insertBulk(Arrays.asList(people));

            int expected = people.length;
            List<Person> results = personDAO.getAll();
            assertEquals(expected, results.size());
        }
    }

    @Test
    public void deletePositive() throws DatabaseException, SQLException {

        try (Connection connection = db.openConnection()) {

            PersonDAO personDAO = new PersonDAO(connection);
            personDAO.insert(person);
            boolean deleted = personDAO.delete("personID_dkindt");
            assertTrue(deleted);

        }
    }

    @Test
    public void deleteNegative() throws DatabaseException, SQLException {

        try (Connection connection = db.openConnection()) {

            PersonDAO personDAO = new PersonDAO(connection);
            boolean deleted = personDAO.delete("INVALID_PERSON_ID");
            assertFalse(deleted);

        }
    }
}