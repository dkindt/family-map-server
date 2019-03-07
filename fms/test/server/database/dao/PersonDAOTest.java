package server.database.dao;

import org.junit.*;
import server.database.Database;
import server.database.model.Person;
import server.exceptions.DatabaseException;

import java.io.IOException;
import java.sql.Connection;

import static org.junit.Assert.*;

public class PersonDAOTest {

    private Database database;
    private Person person;

    @BeforeClass
    public static void setupDatabase() {
        Database db = new Database();
        String path = "src/server/database/sql/db_init.sql";
        try {
            db.init(path);
        } catch(IOException e) {
            System.out.printf("Unable to load SQL file: '%s'\n", path);
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
    }

    @Before
    public void setUp() {

        database = new Database();
        person = new Person();
        person.setUUID("test-uuid");
        person.setDescendant("test-descendant");
        person.setFirstName("test-first-name");
        person.setLastName("test-last-name");
        person.setGender("M-test-gender");
        person.setFather("test-father");
        person.setMother("test-mother");
        person.setSpouse("test-spouse");
    }

    @After
    public void tearDown() throws Exception {
        database.clear();
    }

    @Test
    public void createPositive() throws DatabaseException {
        Connection connection = database.openConnection();
        boolean created = false;
        boolean commit = false;
        try {
            PersonDAO personDAO = new PersonDAO(connection);
            created = personDAO.create(person);
            commit = true;
        } catch (DatabaseException e) {
            e.printStackTrace();
        } finally {
            database.closeConnection(commit);
        }
        assertTrue(created);
    }

    @Test
    public void createNegative() throws DatabaseException {

        boolean created = false;
        try {
            Connection connection = database.openConnection();
            PersonDAO personDAO = new PersonDAO(connection);
            personDAO.create(person);
            personDAO.create(person);
            database.closeConnection(true);
        } catch (DatabaseException e) {
            database.closeConnection(false);
            created = false;
        }
        assertFalse(created);
        Person compare = person;
        try {
            Connection connection = database.openConnection();
            PersonDAO personDAO = new PersonDAO(connection);
            compare = personDAO.get(person.getUUID());
            database.closeConnection(true);
        } catch (DatabaseException e) {
            database.closeConnection(false);
        }

        assertNull(compare);
    }

    @Test
    public void getPositive() throws DatabaseException {

        boolean commit = false;
        String expectedUUID = person.getUUID();
        String actualUUID;
        try {
            Connection connection = database.openConnection();
            PersonDAO personDAO = new PersonDAO(connection);
            personDAO.create(person);
            Person p = personDAO.get(expectedUUID);
            actualUUID = p.getUUID();
            commit = true;
        } finally {
            database.closeConnection(commit);
        }
        assertEquals(expectedUUID, actualUUID);
    }

    @Test
    public void getNegative() throws DatabaseException {

        boolean commit = false;
        try {
            Connection connection = database.openConnection();
            PersonDAO personDAO = new PersonDAO(connection);
            person = personDAO.get("invalid-uuid");
            commit = true;
        } finally {
            database.closeConnection(commit);
        }
        assertNull(person);
    }

    @Test
    public void getAll() {
    }

    @Test
    public void deletePositive() throws DatabaseException {
        boolean deleted;
        boolean commit = false;
        try {
            Connection connection = database.openConnection();
            PersonDAO personDAO = new PersonDAO(connection);
            personDAO.create(person);
            deleted = personDAO.delete("test-uuid");
            commit = true;
        } finally {
            database.closeConnection(commit);
        }
        assertTrue(deleted);
    }

    @Test
    public void deleteNegative() throws DatabaseException {
        boolean deleted;
        boolean commit = false;
        try {
            Connection connection = database.openConnection();
            PersonDAO personDAO = new PersonDAO(connection);
            deleted = personDAO.delete("_test_uuid");
            commit = true;
        } finally {
            database.closeConnection(commit);
        }
        assertFalse(deleted);
    }
}