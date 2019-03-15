package server.services;

import org.junit.Before;
import org.junit.BeforeClass;
import server.database.Database;
import server.exceptions.DatabaseException;
import shared.request.LoadRequest;
import shared.request.LoginRequest;
import shared.result.LoginResult;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static shared.util.FileHelper.getAbsolutePath;
import static shared.util.FileHelper.readJsonFile;

public class ServiceTest {

    private static final String sqlFile = getAbsolutePath("sql/db_init.sql");

    protected String authToken;
    protected String username;
    protected String personID;

    public String getAuthToken() {
        return authToken;
    }

    public String getUsername() {
        return username;
    }

    public String getPersonID() {
        return personID;
    }

    @BeforeClass
    public static void setupDatabase() throws DatabaseException, IOException {

        Database db = new Database();
        db.init(sqlFile);
        db.clear();
    }

    @Before
    public void setUp() throws DatabaseException, FileNotFoundException {

        final String path = getAbsolutePath("data/json/example.json");
        final LoadRequest request = (LoadRequest) readJsonFile(path, LoadRequest.class);

        LoadService service = new LoadService();
        service.load(request);

        LoginService loginService = new LoginService();
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("dkindt");
        loginRequest.setPassword("password");
        LoginResult loginResult = loginService.login(loginRequest);

        authToken = loginResult.getAuthToken();
        username = loginResult.getUsername();
        personID = loginResult.getPersonID();
    }

    // helpers
    boolean validateResponseMsg(String pattern, String input) {
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(input);
        return matcher.find();
    }
}
