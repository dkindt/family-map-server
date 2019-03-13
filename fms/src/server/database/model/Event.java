package server.database.model;

import static shared.util.DatabaseHelper.generateUUID;

/** Represents an Event row in the events table. */
public class Event extends BaseModel {

    private String eventID;
    private String descendant;
    private String personID;
    private float latitude;
    private float longitude;
    private String country;
    private String city;
    private String eventType;
    private int year;

    public Event() {
        this.eventID = generateUUID();
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String id) {
        this.eventID = id;
    }

    public String getDescendant() {
        return descendant;
    }

    public void setDescendant(String descendant) {
        this.descendant = descendant;
    }

    public String getPerson() {
        return personID;
    }

    public void setPerson(String person) {
        this.personID = person;
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

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String type) {
        this.eventType = type;
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
            event.setEventID(getEventID());
            event.setDescendant(getDescendant());
            event.setPerson(getPerson());
            event.setLatitude(getLatitude());
            event.setLongitude(getLongitude());
            event.setCountry(getCountry());
            event.setCity(getCity());
            event.setEventType(getEventType());
            event.setYear(getYear());
            return event;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("\nEvent(");
        sb.append(toStringHelper("eventID"));
        sb.append(toStringHelper("descendant"));
        sb.append(toStringHelper("person"));
        sb.append(toStringHelper("latitude"));
        sb.append(toStringHelper("longitude"));
        sb.append(toStringHelper("country"));
        sb.append(toStringHelper("city"));
        sb.append(toStringHelper("eventType"));
        sb.append(toStringHelper("year"));
        sb.append("\n)");
        String format = sb.toString();
        return String.format(
            format,
            getEventID(),
            getDescendant(),
            getPerson(),
            getLatitude(),
            getLongitude(),
            getCountry(),
            getCity(),
            getEventType(),
            getYear()
        );
    }
}