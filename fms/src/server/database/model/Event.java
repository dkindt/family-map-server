package server.database.model;

import static shared.util.DatabaseHelper.generateUUID;

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

    public Event() {
        this.id = generateUUID();
    }

    public String getUUID() {
        return id;
    }

    public void setUUID(String id) {
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

    @Override
    public Event clone() {
        try {
            return (Event) super.clone();
        } catch (CloneNotSupportedException e) {
            Event event = new Event();
            event.setUUID(getUUID());
            event.setDescendant(getDescendant());
            event.setPerson(getPerson());
            event.setLatitude(getLatitude());
            event.setLongitude(getLongitude());
            event.setCountry(getCountry());
            event.setCity(getCity());
            event.setType(getType());
            event.setYear(getYear());
            return event;
        }
    }
}