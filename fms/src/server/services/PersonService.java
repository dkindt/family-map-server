package server.services;

import server.database.model.Person;
import shared.result.MultiPersonResult;
import shared.result.SinglePersonResult;

/** Provides service for retrieving Persons for a User. */
public class PersonService {

    /**
     * Retrieve a single Person from the database.
     *
     * @param personID UUID for a Person
     * @return SinglePersonResult object.
     */
    public SinglePersonResult getPerson(Person personID) {
        return null;
    }

    /**
     * Retrieves a list of Person for the currently logged-in User.
     * @return MultiPersonResult object.
     */
    public MultiPersonResult getAllPersons() {
        return null;
    }

}
