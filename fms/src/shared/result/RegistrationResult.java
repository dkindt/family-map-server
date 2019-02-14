package shared.result;

/** Represents the result to be returned to the RegistrationService.  */
public class RegistrationResult {

    private String authToken;
    private String username;
    private String personID;
    private String message;

    public RegistrationResult(String authToken, String username, String personID, String message) {
        this.authToken = authToken;
        this.username = username;
        this.personID = personID;
        this.message = message;
    }

}