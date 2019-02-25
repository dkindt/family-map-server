package server.database.model;

import java.sql.ResultSet;
import java.sql.SQLException;

/** Represents an Event row in the events table. */
public class Event {

    private String id;
    private String descendant;
    private String person;
    private float latitude;
    private float longitude;
    private String country;
    private String city;
    private String type;
    private int year;

    public Event(ResultSet resultSet) throws SQLException {
        this(
            resultSet.getString("id"),
            resultSet.getString("descendant"),
            resultSet.getString("person"),
            resultSet.getFloat("latitude"),
            resultSet.getFloat("longitude"),
            resultSet.getString("country"),
            resultSet.getString("city"),
            resultSet.getString("type"),
            resultSet.getInt("year")
        );
    }

    /**
     * Builds an Event object using the following:
     * @param id Unique identifier for the event (non-empty)
     * @param descendant User (username) to which person belongs to
     * @param person personID to which this event belongs
     * @param latitude of Event's location
     * @param longitude of Event's location
     * @param country in which the Event occurred
     * @param city in which the Event occurred
     * @param type of Event (birth, baptism, christening, marriage, death, etc.)
     * @param year in which the Event occurred.
     */
    public Event(String id, String descendant, String person,
                 float latitude, float longitude, String country,
                 String city, String type, int year) {
        setID(id);
        setDescendant(descendant);
        setPerson(person);
        setLatitude(latitude);
        setLongitude(longitude);
        setCountry(country);
        setCity(city);
        setType(type);
        setYear(year);
    }

    public String getId() {
        return id;
    }

    public void setID(String id) {
        this.id = id;
    }

    public String getDescendant() {
        return descendant;
    }

    public void setDescendant(String descendant) {
        this.descendant = descendant;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}