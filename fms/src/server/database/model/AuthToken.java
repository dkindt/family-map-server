package server.database.model;

public class AuthToken {

    private String token;
    private String userName;

    public AuthToken(String token, String userName) {
        setTokenID(token);
        setUserName(userName);
    }

    public String getToken() { return this.token; }
    public String getUserName() { return this.userName; }

    public void setTokenID(String tokenID) {
        this.token = tokenID;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}