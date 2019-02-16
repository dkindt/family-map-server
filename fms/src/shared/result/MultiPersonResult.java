package shared.result;

import server.database.model.Person;

/** Represents response message or data to be returned to the Person service.  */
public class MultiPersonResult {

    private Person[] persons;
    private String message;

    public MultiPersonResult(Person[] persons, String message) {
        this.persons = persons;
        this.message = message;
    }

    public Person[] getPersons() {
        return persons;
    }

    public void setPersons(Person[] persons) {
        this.persons = persons;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
