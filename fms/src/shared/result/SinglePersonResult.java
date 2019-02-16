package shared.result;

import server.database.model.Person;

/** Represents response message or data to be returned to the Person service.  */
public class SinglePersonResult {

    private Person person;
    private String message;

    public SinglePersonResult(Person person) {
        this.person = person;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
