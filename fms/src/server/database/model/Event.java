package server.database.model;

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

    public String getID() { return this.id; }
    public String getDescendant() { return this.descendant; }
    public String getPerson() { return this.person; }
    public float getLatitude() { return this.latitude; }
    public float getLongitude() { return this.longitude; }
    public String getCountry() { return this.country; }
    public String getCity() { return this.city; }
    public String getType() { return this.type; }
    public int getYear() { return this.year; }

    public void setID(String id) {
        this.id = id;
    }

    public void setDescendant(String descendant) {
        this.descendant = descendant;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

}