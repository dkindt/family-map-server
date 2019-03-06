package server.database.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import server.database.Database;
import server.database.model.Person;
import server.database.model.User;
import server.exceptions.DatabaseException;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.Assert.*;

public class UserDAOTest {

    private Database database;
    private User user;

    @BeforeClass
    public static void setupDatabase() {
        Database db = new Database();
        String path = "sql/db_init.sql";
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
        user = new User();
        user.setUsername("test-username");
        user.setPassword("test-password");
        user.setEmail("test-email");
        user.setFirstName("test-first-name");
        user.setLastName("test-last-name");
        user.setGender("M-test-gender");
        user.setPersonID("test-person-id");
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
            UserDAO userDAO = new UserDAO(connection);
            created = userDAO.create(user);
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

        boolean created = true;
        boolean commit = false;
        try {
            Connection connection = database.openConnection();
            UserDAO userDAO = new UserDAO(connection);
            userDAO.create(user);
            created = userDAO.create(user);
        } catch(DatabaseException e) {
            created = false;
        } finally {
            database.closeConnection(commit);
        }
        assertFalse(created);
    }

    @Test
    public void getWithParams() throws DatabaseException {

        boolean commit = false;
        Map<String, String> params = new LinkedHashMap<>();
        params.put("username", "test-username");
        params.put("password", "test-password");
        String expectedUsername = user.getUsername();
        String actualUsername;
        try {
            Connection connection = database.openConnection();
            UserDAO userDAO = new UserDAO(connection);
            userDAO.create(user);
            User u = userDAO.get(params);
            actualUsername = u.getUsername();
            commit = true;
        } finally {
            database.closeConnection(commit);
        }
        assertEquals(expectedUsername, actualUsername);
    }

    @Test
    public void getPositive() throws DatabaseException {

        boolean commit = false;
        String expectedUsername = user.getUsername();
        String actualUsername;
        try {
            Connection connection = database.openConnection();
            UserDAO userDAO = new UserDAO(connection);
            userDAO.create(user);
            User u = userDAO.get(expectedUsername);
            actualUsername = u.getUsername();
            commit = true;
        } finally {
            database.closeConnection(commit);
        }
        assertEquals(expectedUsername, actualUsername);
    }

    @Test
    public void getNegative() throws DatabaseException {

        boolean commit = false;
        try {
            Connection connection = database.openConnection();
            UserDAO userDAO = new UserDAO(connection);
            user = userDAO.get("invalid-username");
            commit = true;
        } finally {
            database.closeConnection(commit);
        }
        assertNull(user);
    }

    @Test
    public void getAll() {
    }

    @Test
    public void deletePositive() throws DatabaseException {

        boolean commit = false;
        boolean deleted;
        try {
            Connection connection = database.openConnection();
            UserDAO userDAO = new UserDAO(connection);
            userDAO.create(user);
            deleted = userDAO.delete("_test_uuid");
            commit = true;
        } finally {
            database.closeConnection(commit);
        }
        assertFalse(deleted);
    }

    @Test
    public void deleteNegative() throws DatabaseException {

        boolean commit = false;
        boolean deleted;
        try {
            Connection connection = database.openConnection();
            UserDAO userDAO = new UserDAO(connection);
            deleted = userDAO.delete("_test_uuid");
            commit = true;
        } finally {
            database.closeConnection(commit);
        }
        assertFalse(deleted);
    }
}