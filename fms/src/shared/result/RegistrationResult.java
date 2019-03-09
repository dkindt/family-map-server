package shared.result;

/** Represents the result to be returned to the RegistrationService.  */
public class RegistrationResult extends BaseResult {

    private String authToken;
    private String username;
    private String personID;

    public RegistrationResult(String message) {
        setMessage(message);
    }

    public RegistrationResult(String authToken, String username, String personID) {
        setAuthToken(authToken);
        setUsername(username);
        setPersonID(personID);
    }

    public RegistrationResult(String authToken, String username, String personID, String message) {
        setAuthToken(authToken);
        setUsername(username);
        setPersonID(personID);
        setMessage(message);
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