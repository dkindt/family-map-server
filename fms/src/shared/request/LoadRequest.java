package shared.request;

import server.database.model.Event;
import server.database.model.Person;
import server.database.model.User;

/** Represents the request body for the LoadService. */
public class LoadRequest {

    private User[] users;
    private Person[] persons;
    private Event[] events;

    /**
     * Build the request body for the LoadService.
     * @param users: List of User objects.
     * @param persons: List of Person objects.
     * @param events: List of Event objects.
     */
    public LoadRequest(User[] users, Person[] persons, Event[] events) {
        setUsers(users);
        setPersons(persons);
        setEvents(events);
    }

    public User[] getUsers() {
        return users;
    }

    public void setUsers(User[] users) {
        this.users = users;
    }

    public Person[] getPersons() {
        return persons;
    }

    public void setPersons(Person[] persons) {
        this.persons = persons;
    }

    public Event[] getEvents() {
        return events;
    }

    public void setEvents(Event[] events) {
        this.events = events;
    }
}
