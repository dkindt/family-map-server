package server.services;

import org.junit.Test;
import server.database.model.Event;
import server.exceptions.AuthenticationException;
import server.exceptions.DatabaseException;
import shared.result.EventResult;

import static org.junit.Assert.*;

public class EventServiceTest extends ServiceTest {

    @Test
    public void getEvent() throws DatabaseException {

        final String expectedEventID = "marriage_event_format";

        try {
            EventService service = new EventService();
            EventResult result = service.getEvent(
                expectedEventID,
                getAuthToken()
            );

            Event[] data = result.getData();
            assertEquals(1, data.length);

            Event event = data[0];
            assertEquals(expectedEventID, event.getEventID());

        } catch (AuthenticationException e) {
            System.out.println("AuthenticationException thrown in getEvent()");
        }
    }

    @Test
    public void getAllEvents() throws DatabaseException {

        try {
            final int expectedCount = 2;
            EventService service = new EventService();
            EventResult result = service.getAllEvents(getAuthToken());

            Event[] data = result.getData();
            assertEquals(expectedCount, data.length);

        } catch (AuthenticationException e) {
            System.out.println("AuthenticationException thrown in getEvent()");
        }
    }
}