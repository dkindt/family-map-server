package shared.request;

/** Represents the request body for the RegistrationService. */
public class RegistrationRequest {

    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String gender;

    public RegistrationRequest(String username, String password, String email, String firstName, String lastName, String gender) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
    }
}