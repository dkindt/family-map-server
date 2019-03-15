package server.services;

import org.junit.Test;
import shared.result.ClearResult;

import static org.junit.Assert.*;

public class ClearServiceTest extends ServiceTest {

    @Test
    public void clear() {

        final String expectedMsg = "^[0-9]+ total rows deleted\\.$";
        ClearService service = new ClearService();
        ClearResult result = service.clear();
        boolean success = validateResponseMsg(expectedMsg, result.getMessage());
        assertTrue(success);
    }
}