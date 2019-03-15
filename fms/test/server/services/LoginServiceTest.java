package server.services;

import org.junit.Test;
import server.exceptions.DatabaseException;
import shared.request.LoginRequest;
import shared.result.LoginResult;

import static org.junit.Assert.*;

public class LoginServiceTest extends ServiceTest {

    @Test
    public void login() throws DatabaseException {

        LoginService loginService = new LoginService();
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("dkindt");
        loginRequest.setPassword("password");
        LoginResult loginResult = loginService.login(loginRequest);

        String token = loginResult.getAuthToken();
        String username = loginResult.getUsername();
        String personID = loginResult.getPersonID();

        assertNotNull(token);
        assertEquals(username, getUsername());
        assertEquals(personID, getPersonID());
    }
}