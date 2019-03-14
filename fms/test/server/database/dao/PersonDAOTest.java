package server.database.dao;

import org.junit.*;
import server.database.Database;
import server.database.model.Person;
import server.exceptions.DatabaseException;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class PersonDAOTest {

    private static Database db;
    private Person person;

    @BeforeClass
    public static void setupDatabase() {

        db = new Database();
        String path = "/Users/dkindt/Documents/school/2019/winter/cs240/projects/family-map/sql/db_init.sql";
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
    public void createPositive() throws DatabaseException {

        try (Connection connection = db.openConnection()) {

            PersonDAO personDAO = new PersonDAO(connection);
            boolean inserted = personDAO.insert(person);
            assertTrue(inserted);

        } catch (SQLException e) {
            throw new DatabaseException("Failed in createPositive", e);
        }
    }

    @Test
    public void createNegative() throws SQLException {

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
    public void getAll() {
    }

    @Test
    public void deletePositive() throws DatabaseException {

        try (Connection connection = db.openConnection()) {

            PersonDAO personDAO = new PersonDAO(connection);
            personDAO.insert(person);
            boolean deleted = personDAO.delete("personID_dkindt");
            assertTrue(deleted);

        } catch (SQLException e) {

            throw new DatabaseException("failed deletePositive", e);
        }
    }

    @Test
    public void deleteNegative() throws DatabaseException {

        try (Connection connection = db.openConnection()) {

            PersonDAO personDAO = new PersonDAO(connection);
            boolean deleted = personDAO.delete("INVALID_PERSON_ID");
            assertFalse(deleted);

        } catch (SQLException e) {
            throw new DatabaseException("failed deleteNegative", e);
        }
    }
}