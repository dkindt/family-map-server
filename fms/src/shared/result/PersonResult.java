package shared.result;

import server.database.model.Person;

/** Represents response message or data to be returned to the Person service.  */
public class PersonResult extends BaseResult {

    private Person[] data;

    public PersonResult(Person[] data) {
        this.data = data;
    }
}
