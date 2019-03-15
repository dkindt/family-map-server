package server.database.model;

/** Represents an authorization token for a User. A row in the `users` table. */
public class AuthToken extends BaseModel {

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
    public AuthToken clone() {
        try {
            return (AuthToken) super.clone();
        } catch (CloneNotSupportedException e) {
            AuthToken token = new AuthToken();
            token.setToken(getToken());
            token.setUsername(getUserName());
            return token;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("\nAuthToken(");
        sb.append(toStringHelper("token"));
        sb.append(toStringHelper("username"));
        sb.append("\n)");
        String format = sb.toString();
        return String.format(
            format,
            getToken(),
            getUserName()
        );
    }
}