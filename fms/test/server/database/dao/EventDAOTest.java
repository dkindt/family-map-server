package server.database.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import server.database.Database;
import server.database.model.Event;
import server.exceptions.DatabaseException;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static shared.util.DatabaseHelper.generateUUID;
import static shared.util.FileHelper.getAbsolutePath;

public class EventDAOTest {

    private static Database db;
    private Event event;

    @BeforeClass
    public static void setupDatabase() throws DatabaseException, IOException {

        final String path = getAbsolutePath("sql/db_init.sql");
        db = new Database();
        db.init(path);
    }

    @Before
    public void setUp() {

        event = new Event();
        event.setEventID("EVENT_ID");
        event.setDescendant("descendant");
        event.setPerson("dkindt");
        event.setLatitude(69);
        event.setLongitude(-69);
        event.setCountry("USA");
        event.setCity("Provo");
        event.setEventType("CS240");
        event.setYear(1969);
    }

    @After
    public void tearDown() throws DatabaseException {
        db.clear();
    }

    @Test
    public void insertPositive() throws DatabaseException, SQLException {

        try (Connection connection = db.openConnection()) {

            EventDAO eventDAO = new EventDAO(connection);

            // Test for inserting a single record into the database.
            boolean inserted = eventDAO.insert(event);
            assertTrue(inserted);

            // Test for bulk inserting multiple records into the database.
            Event event1 = event.clone();
            Event event2 = event.clone();
            Event event3 = event.clone();
            event1.setEventID(generateUUID());
            event2.setEventID(generateUUID());
            event3.setEventID(generateUUID());
            Event[] events = new Event[]{
                event1,
                event2,
                event3
            };
            int expected = 3;
            int actual = eventDAO.insertBulk(Arrays.asList(events));
            assertEquals(expected, actual);
        }
    }

    @Test
    public void insertNegative() throws SQLException {

        try (Connection connection = db.openConnection()) {

            EventDAO eventDAO = new EventDAO(connection);
            eventDAO.insert(event);
            eventDAO.insert(event);

        } catch(DatabaseException e) {

            final String check = "UNIQUE constraint failed: events.eventID";
            assertTrue(e.getMessage().contains(check));
        }
    }

    @Test
    public void getPositive() throws DatabaseException, SQLException {

        String actualID;
        String expectedID = event.getEventID();
        try (Connection connection = db.openConnection()) {

            EventDAO eventDAO = new EventDAO(connection);
            eventDAO.insert(event);
            Event actual = eventDAO.get(expectedID);
            actualID = actual.getEventID();
            assertEquals(expectedID, actualID);
        }
    }

    @Test
    public void getNegative() throws DatabaseException, SQLException {

        try (Connection connection = db.openConnection()) {

            EventDAO eventDAO = new EventDAO(connection);
            Event event = eventDAO.get("INVALID");
            assertNull(event);
        }
    }

    @Test
    public void getAll() throws DatabaseException, SQLException {

        try (Connection connection = db.openConnection()) {

            EventDAO eventDAO = new EventDAO(connection);
            Event event1 = event.clone();
            event1.setEventID(generateUUID());
            Event event2 = event.clone();
            event2.setEventID(generateUUID());
            Event event3 = event.clone();
            event3.setEventID(generateUUID());
            Event event4 = event.clone();
            event4.setEventID(generateUUID());
            Event[] events = new Event[]{
                event,
                event1,
                event2,
                event3,
                event4
            };
            eventDAO.insertBulk(Arrays.asList(events));

            int expected = events.length;
            List<Event> results = eventDAO.getAll();
            assertEquals(expected, results.size());
        }
    }

    @Test
    public void deletePositive() throws DatabaseException {

        try (Connection connection = db.openConnection()) {

            EventDAO eventDAO = new EventDAO(connection);
            eventDAO.insert(event);
            boolean deleted = eventDAO.delete("EVENT_ID");
            assertTrue(deleted);

        } catch (SQLException e) {

            throw new DatabaseException("failed deletePositive", e);
        }
    }

    @Test
    public void deleteNegative() throws DatabaseException, SQLException {

        try (Connection connection = db.openConnection()) {

            EventDAO eventDAO = new EventDAO(connection);
            boolean deleted = eventDAO.delete("INVALID");
            assertFalse(deleted);

        }
    }
}