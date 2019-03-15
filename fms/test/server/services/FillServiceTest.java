package server.services;

import org.junit.Test;
import server.exceptions.DatabaseException;
import shared.result.FillResult;

import static org.junit.Assert.*;

public class FillServiceTest extends ServiceTest {

    @Test
    public void fill() throws DatabaseException {

        final String expectedMsg = "^Successfully added [0-9]+ persons and [0-9]+ events$";
        final int generations = 4;

        FillService service = new FillService();
        FillResult result = service.fill(getUsername(), generations);
        assertTrue(validateResponseMsg(expectedMsg, result.getMessage()));
    }
}