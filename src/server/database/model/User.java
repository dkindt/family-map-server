package server.database.model;

public class User {

    private String userName;
    private String password; // TODO: should be a hash
    private String email;
    private String firstName;
    private String lastName;
    private String gender;
    private String personID;

    public User(String userName, String password, String email, String firstName,
                String lastName, String gender, String personID) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.personID = personID;
    }

    public String getUserName() { return this.userName; }
    public String getEmail() { return this.email; }
    public String getFirstName() { return this.firstName; }
    public String getLastName() { return this.lastName; }
    public String getGender() { return this.gender; }
    public String getPersonID() { return this.personID; }

}