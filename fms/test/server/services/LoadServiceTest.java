package server.services;

import org.junit.Test;
import server.exceptions.DatabaseException;
import shared.request.LoadRequest;
import shared.result.LoadResult;

import java.io.FileNotFoundException;

import static org.junit.Assert.*;
import static shared.util.FileHelper.getAbsolutePath;
import static shared.util.FileHelper.readJsonFile;

public class LoadServiceTest extends ServiceTest {

    @Test
    public void load() throws DatabaseException, FileNotFoundException {

        final String path = getAbsolutePath("data/json/example.json");
        final LoadRequest request = (LoadRequest) readJsonFile(path, LoadRequest.class);
        final String expectedMsg = "^Successfully added [0-9]+ users, [0-9]+ persons, and [0-9]+ events$";

        LoadService service = new LoadService();
        LoadResult result = service.load(request);
        System.out.println(String.format("'%s'", result.getMessage()));
        assertTrue(validateResponseMsg(expectedMsg, result.getMessage()));
    }
}