package server.database.model;

public class AuthToken {

    private String tokenID;
    private String userName;

    public AuthToken(String id, String userName) {
        this.tokenID = id;
        this.userName = userName;
    }

    public String getTokenID() { return this.tokenID; }
    public String getUserName() { return this.userName; }
}