package server.database.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import server.database.Database;
import server.database.model.User;
import server.exceptions.DatabaseException;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static shared.util.DatabaseHelper.generateUUID;
import static shared.util.FileHelper.getAbsolutePath;

public class UserDAOTest {

    private static Database db;
    private User user;

    @BeforeClass
    public static void setupDatabase() throws DatabaseException, IOException {

        final String path = getAbsolutePath("sql/db_init.sql");
        db = new Database();
        db.init(path);
    }

    @Before
    public void setUp() throws Exception {

        db = new Database();
        user = new User();
        user.setUsername("dkindt");
        user.setPassword("test-password");
        user.setEmail("test-email");
        user.setFirstName("test-first-name");
        user.setLastName("test-last-name");
        user.setGender("M-test-gender");
        user.setPersonID("test-person-id");
    }

    @After
    public void tearDown() throws Exception {
        db.clear();
    }

    @Test
    public void insertPositive() throws DatabaseException, SQLException {

        try (Connection connection = db.openConnection()) {

            UserDAO userDAO = new UserDAO(connection);
            boolean created = userDAO.insert(user);
            assertTrue(created);
        }
    }

    @Test
    public void insertNegative() throws SQLException {

        try (Connection connection = db.openConnection()) {

            UserDAO userDAO = new UserDAO(connection);
            userDAO.insert(user);
            userDAO.insert(user);

        } catch (DatabaseException e) {

            final String check = "UNIQUE constraint failed: users.username";
            assertTrue(e.getMessage().contains(check));
        }
    }

    @Test
    public void getWithParams() throws DatabaseException, SQLException {

        Map<String, String> params = new LinkedHashMap<>();
        params.put("username", "dkindt");
        params.put("password", "test-password");
        String expectedUsername = user.getUsername();
        String actualUsername;

        try (Connection connection = db.openConnection()) {

            UserDAO userDAO = new UserDAO(connection);
            userDAO.insert(user);
            User u = userDAO.get(params);
            actualUsername = u.getUsername();
            assertEquals(expectedUsername, actualUsername);
        }
    }

    @Test
    public void getPositive() throws DatabaseException, SQLException {

        String expectedUsername = user.getUsername();
        String actualUsername;

        try (Connection connection = db.openConnection()) {

            UserDAO userDAO = new UserDAO(connection);
            userDAO.insert(user);
            User u = userDAO.get(expectedUsername);
            actualUsername = u.getUsername();
            assertEquals(expectedUsername, actualUsername);
        }
    }

    @Test
    public void getNegative() throws DatabaseException, SQLException {

        try (Connection connection = db.openConnection()) {

            UserDAO userDAO = new UserDAO(connection);
            User u = userDAO.get("INVALID");
            assertNull(u);
        }
    }

    @Test
    public void getAll() throws DatabaseException, SQLException {

        try (Connection connection = db.openConnection()) {

            UserDAO userDAO = new UserDAO(connection);
            User user1 = user.clone();
            user1.setUsername(generateUUID());
            User user2 = user.clone();
            user2.setUsername(generateUUID());
            User user3 = user.clone();
            user3.setUsername(generateUUID());
            User user4 = user.clone();
            user4.setUsername(generateUUID());
            User[] users = new User[]{
                user,
                user1,
                user2,
                user3,
                user4
            };
            userDAO.insertBulk(Arrays.asList(users));

            int expected = users.length;
            List<User> results = userDAO.getAll();
            assertEquals(expected, results.size());
        }
    }

    @Test
    public void deletePositive() throws DatabaseException, SQLException {

        try (Connection connection = db.openConnection()) {

            UserDAO userDAO = new UserDAO(connection);
            userDAO.insert(user);
            boolean deleted = userDAO.delete("dkindt");
            assertTrue(deleted);
        }
    }

    @Test
    public void deleteNegative() throws DatabaseException, SQLException {

        try (Connection connection = db.openConnection()) {

            UserDAO userDAO = new UserDAO(connection);
            boolean deleted = userDAO.delete("INVALID");
            assertFalse(deleted);
        }
    }
}