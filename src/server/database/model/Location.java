package server.database.model;

public class Location {

    private int id;
    private float latitude;
    private float longitude;
    private String country;
    private String city;

    public Location(int id, float latitude, float longitude, String country, String city) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
    }

    public int getId() { return this.id; }
    public float getLatitude() { return this.latitude; }
    public float getLongitude() { return this.longitude; }
    public String getCountry() { return this.country; }
    public String getCity() { return this.city; }
}
