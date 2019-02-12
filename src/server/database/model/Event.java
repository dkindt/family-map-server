package server.database.model;

public class Event {

    private String id;
    private String descendant;
    private String person;
    private Location location;
    private String type;
    private int year;

    public Event(String id, String descendant, String person,
                 Location location, String type, int year) {
        this.id = id;
        this.descendant = descendant;
        this.person = person;
        this.location = location;
        this.type = type;
        this.year = year;
    }

    public String getID() { return this.id; }
    public String getDescendant() { return this.descendant; }
    public String getPerson() { return this.person; }
    public Location getLocation() { return this.location; }
    public String getEventType() { return this.type; }
    public int getYear() { return this.year; }


}