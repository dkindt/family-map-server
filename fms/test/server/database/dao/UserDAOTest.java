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

import static org.junit.Assert.*;

public class UserDAOTest {

    private Database database;
    private User user;

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
        user = new User(
            "_test_username",
            "_test_password",
            "_test_email",
            "_test_first_name",
            "_test_last_name",
            "M",
            "_test_person_id"
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

    @Test(expected = DatabaseException.class)
    public void createNegative() throws DatabaseException {
        boolean created = false;
        boolean commit = false;
        Connection connection = database.openConnection();
        User user1 = new User(
            "_test_username",
            "_test_password",
            "_test_email",
            "_test_first_name",
            "_test_last_name",
            "M",
            "_test_person_id"
        );
        try {
            UserDAO userDAO = new UserDAO(connection);
            created = userDAO.create(user);
            created = userDAO.create(user1);
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
        String expectedUsername = user.getUsername();
        String actualUsername;
        try {
            UserDAO userDAO = new UserDAO(connection);
            userDAO.create(user);
            User u = userDAO.get(expectedUsername);
            actualUsername = u.getUsername();
            commit = true;
        } finally {
            database.closeConnection(commit);
        }
        assert expectedUsername.equals(actualUsername);
        //assertEquals(expectedUsername, actualUsername);
    }

    @Test(expected = DatabaseException.class)
    public void getNegative() throws DatabaseException {
        boolean commit = false;
        Connection connection = database.openConnection();
        String badUsername = "invalid-username-id";
        try {
            UserDAO userDAO = new UserDAO(connection);
            user = userDAO.get(badUsername);
            commit = true;
        } finally {
            database.closeConnection(commit);
        }
    }

    @Test
    public void getAll() {
    }

    @Test
    public void deletePositive() throws DatabaseException {
        boolean deleted = false;
        try {
            Connection connection = database.openConnection();
            UserDAO userDAO = new UserDAO(connection);
            boolean created = userDAO.create(user);
            if (created) {
                deleted = userDAO.delete(user.getUsername());
            }
            database.closeConnection(true);
        } catch (DatabaseException e) {
            e.printStackTrace();
            database.closeConnection(false);
        }
        assertTrue(deleted);
    }

    @Test
    public void deleteNegative() throws DatabaseException {

        boolean deleted = false;
        try {
            Connection connection = database.openConnection();
            UserDAO userDAO = new UserDAO(connection);
            userDAO.create(user);
            database.closeConnection(true);
        } catch (DatabaseException e) {
            e.printStackTrace();
            database.closeConnection(false);
        }
        assertTrue(deleted);
    }
}