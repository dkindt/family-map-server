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
    public void setUp() throws Exception {

        database = new Database();
        person = new Person(
            "_test_uuid",
            "_test_descendant",
            "_test_firstName",
            "_test_lastName",
            "M",
            "_test_father",
            "_test_mother",
            "_test_spouse"
        );
    }

    @After
    public void tearDown() throws Exception {
        database.clearTables();
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

    @Test(expected = DatabaseException.class)
    public void createNegative() throws DatabaseException {
        boolean created = false;
        boolean commit = false;
        Connection connection = database.openConnection();
        Person person1 = new Person(
            "_test_uuid",
            "_test_descendant",
            "_test_firstName",
            "_test_lastName",
            "M",
            "_test_father",
            "_test_mother",
            "_test_spouse"
        );
        try {
            PersonDAO personDAO = new PersonDAO(connection);
            created = personDAO.create(person);
            created = personDAO.create(person1);
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw e;
        } finally {
            database.closeConnection(commit);
        }
        assertTrue(created);

    }

    @Test
    public void getPositive() throws DatabaseException {
        boolean commit = false;
        Connection connection = database.openConnection();
        String expectedUUID = person.getUUID();
        String actualUUID;
        try {
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

    @Test(expected = DatabaseException.class)
    public void getNegative() throws DatabaseException {
        boolean commit = false;
        String badUUID = "invalid-username-id";
        try {
            Connection connection = database.openConnection();
            PersonDAO personDAO = new PersonDAO(connection);
            person = personDAO.get(badUUID);
            commit = true;
        } finally {
            database.closeConnection(commit);
        }
    }

    @Test
    public void getAll() {
    }

    @Test
    public void deletePositive() {

    }

    @Test(expected = DatabaseException.class)
    public void deleteNegative() {

    }
}