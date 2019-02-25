package server.database.model;

import server.exceptions.DatabaseException;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;

/** Represents a User row in the users table. */
public class User {

    private String username;
    private String password; // TODO: should be a hash when set???
    private String email;
    private String firstName;
    private String lastName;
    private String gender;
    private String personID;

    public User(ResultSet resultSet) throws SQLException {
        this(
            resultSet.getString("username"),
            resultSet.getString("password"),
            resultSet.getString("email"),
            resultSet.getString("first_name"),
            resultSet.getString("last_name"),
            resultSet.getString("gender"),
            resultSet.getString("person_id")
        );
    }

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
       setUsername(username);
       setPassword(password);
       setEmail(email);
       setFirstName(firstName);
       setLastName(lastName);
       setGender(gender);
       setPersonID(personID);
    }
    public int getNumFields() {
        getClass().getDeclaredFields();
        return getColumnNames().length;
    }
    public Field[] getColumnNames() {
        return getClass().getDeclaredFields();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

}