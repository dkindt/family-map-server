package server.database.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import server.database.Database;
import server.database.model.AuthToken;
import server.exceptions.DatabaseException;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static shared.util.DatabaseHelper.generateUUID;
import static shared.util.FileHelper.getAbsolutePath;

public class AuthDAOTest {

    private static Database db;
    private AuthToken token;

    @BeforeClass
    public static void setupDatabase() throws DatabaseException, IOException {

        final String path = getAbsolutePath("sql/db_init.sql");
        db = new Database();
        db.init(path);
    }

    @Before
    public void setUp() throws Exception {

        token = new AuthToken();
        token.setUsername("dkindt");
        token.setToken("SAMPLE_AUTH_TOKEN");
    }

    @After
    public void tearDown() throws Exception {
        db.clear();
    }

    @Test
    public void insertPositive() throws DatabaseException, SQLException {

        try (Connection connection = db.openConnection()) {

            AuthDAO authDAO = new AuthDAO(connection);

            boolean inserted = authDAO.insert(token);
            assertTrue(inserted);

            // Test for bulk inserting multiple records into the database.
            AuthToken token1 = token.clone();
            AuthToken token2 = token.clone();
            AuthToken token3 = token.clone();
            token1.setToken(generateUUID());
            token2.setToken(generateUUID());
            token3.setToken(generateUUID());
            AuthToken[] tokens = new AuthToken[]{
                token1,
                token2,
                token3,
            };
            int expected = 3;
            int actual = authDAO.insertBulk(Arrays.asList(tokens));
            assertEquals(expected, actual);
        }
    }

    @Test
    public void insertNegative() throws SQLException {

        try (Connection connection = db.openConnection()) {

            AuthDAO authDAO = new AuthDAO(connection);
            authDAO.insert(token);
            authDAO.insert(token);

        } catch (DatabaseException e) {

            final String check = "UNIQUE constraint failed: auth.token";
            assertTrue(e.getMessage().contains(check));
        }
    }

    @Test
    public void getPositive() throws DatabaseException, SQLException {

        String actualToken;
        String expectedToken = token.getToken();
        try (Connection connection = db.openConnection()) {

            AuthDAO authDAO = new AuthDAO(connection);
            authDAO.insert(token);
            AuthToken actual = authDAO.get(expectedToken);
            actualToken = actual.getToken();
            assertEquals(expectedToken, actualToken);
        }
    }

    @Test
    public void getNegative() throws DatabaseException, SQLException {

        try (Connection connection = db.openConnection()) {

            AuthDAO authDAO = new AuthDAO(connection);
            AuthToken t = authDAO.get("INVALID");
            assertNull(t);
        }
    }

    @Test
    public void getAll() throws DatabaseException, SQLException {

        try (Connection connection = db.openConnection()) {

            AuthDAO authDAO = new AuthDAO(connection);
            AuthToken token1 = token.clone();
            token1.setToken(generateUUID());
            AuthToken token2 = token.clone();
            token2.setToken(generateUUID());
            AuthToken token3 = token.clone();
            token3.setToken(generateUUID());
            AuthToken token4 = token.clone();
            token4.setToken(generateUUID());
            AuthToken[] tokens = new AuthToken[]{
                token,
                token1,
                token2,
                token3,
                token4
            };
            authDAO.insertBulk(Arrays.asList(tokens));

            int expected = tokens.length;
            List<AuthToken> results = authDAO.getAll();
            assertEquals(expected, results.size());
        }
    }

    @Test
    public void deletePositive() throws DatabaseException, SQLException {

        try (Connection connection = db.openConnection()) {

            AuthDAO authDAO = new AuthDAO(connection);
            authDAO.insert(token);
            boolean deleted = authDAO.delete("SAMPLE_AUTH_TOKEN");
            assertTrue(deleted);
        }
    }

    @Test
    public void deleteNegative() throws DatabaseException, SQLException {

        try (Connection connection = db.openConnection()) {

            AuthDAO authDAO = new AuthDAO(connection);
            boolean deleted = authDAO.delete("INVALID");
            assertFalse(deleted);
        }
    }
}