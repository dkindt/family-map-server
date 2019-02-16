package server.database.model;

/** Represents a User row in the users table. */
public class User {

    private String username;
    private String password; // TODO: should be a hash when set???
    private String email;
    private String firstName;
    private String lastName;
    private String gender;
    private String personID;

    /**
     * Generate a new User object:
     * @param username (non-empty, unique)
     * @param password (non-empty)
     * @param email address
     * @param firstName first name of the User registering
     * @param lastName last name of the User registering
     * @param gender gender of the User registering
     * @param personID assigned to this's User's generated Person object.
     */
    public User(String username, String password, String email, String firstName,
                String lastName, String gender, String personID) {
       setUserName(username);
       setPassword(password);
       setEmail(email);
       setFirstName(firstName);
       setLastName(lastName);
       setGender(gender);
       setPersonID(personID);
    }

    public String getUserName() { return this.username; }
    public String getEmail() { return this.email; }
    public String getFirstName() { return this.firstName; }
    public String getLastName() { return this.lastName; }
    public String getGender() { return this.gender; }
    public String getPersonID() { return this.personID; }

    public void setUserName(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

}