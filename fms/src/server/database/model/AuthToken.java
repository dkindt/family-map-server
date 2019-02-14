package server.database.model;

/**
 * Represents an authorization token for a User.
 *
 * @author Dan Kindt
 * @version 1.0
 * @since 15 February 2019
 */
public class AuthToken {

    private String token;
    private String username;

    public AuthToken(String token, String username) {
        setTokenID(token);
        setUserName(username);
    }

    public String getToken() { return this.token; }
    public String getUserName() { return this.username; }

    public void setTokenID(String tokenID) {
        this.token = tokenID;
    }

    public void setUserName(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}