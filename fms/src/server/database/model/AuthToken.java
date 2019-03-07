package server.database.model;

import static java.lang.String.format;

/** Represents an authorization token for a User. A row in the `users` table. */
public class AuthToken {

    private String token;
    private String username;

    public AuthToken() {

    }

    public String getToken() { return this.token; }
    public String getUserName() { return this.username; }

    public void setToken(String tokenID) {
        this.token = tokenID;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return format("AuthToken(token='%s', username='%s')", token, username);
    }
}