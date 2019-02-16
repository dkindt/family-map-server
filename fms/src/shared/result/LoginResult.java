package shared.result;

/** Represents the response data to be returned to the LoginService.  */
public class LoginResult {

    private String authToken;
    private String username;
    private String personID;

    public LoginResult(String authToken, String username, String personID) {
        this.authToken = authToken;
        this.username = username;
        this.personID = personID;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }
}
