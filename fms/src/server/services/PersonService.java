package server.services;

import server.database.Database;
import server.database.dao.PersonDAO;
import server.database.model.Person;
import server.exceptions.AuthenticationException;
import server.exceptions.DatabaseException;
import shared.result.PersonResult;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static java.lang.String.format;


/** Provides service for retrieving Persons for a User. */
public class PersonService extends BaseService {

    private Database database;
    private PersonDAO personDAO;

    public PersonService() {
        this.database = new Database();
    }

    /**
     * Retrieve a single Person from the database.
     *
     * @param uuid UUID for a Person
     * @return SinglePersonResult object.
     */
    public PersonResult getPerson(String uuid, String token)
        throws AuthenticationException, DatabaseException {

        try (Connection connection = database.openConnection()) {

            personDAO = new PersonDAO(connection);
            Person person = personDAO.getFromAuth(uuid, token);
            if (person == null) {
                log.severe("Person not found");
                throw new DatabaseException(
                    format("Person(uuid='%s') does NOT exist!", uuid));
            }
            return new PersonResult(new Person[]{person});

        } catch (SQLException e) {
            throw new DatabaseException("Failed to get Person!", e);
        }
    }

    /**
     * Retrieves a list of Person for the currently logged-in User.
     * @return PersonResult object.
     */
    public PersonResult getAllPersons(String token)
        throws AuthenticationException, DatabaseException {

        try (Connection connection = database.openConnection()) {

            personDAO = new PersonDAO(connection);
            List<Person> persons = personDAO.getAllFromToken(token);
            Person[] data = new Person[persons.size()];
            data = persons.toArray(data);

            if (data.length != 0) {
                return new PersonResult(data);
            }
            return new PersonResult("No persons found!");

        } catch (SQLException e) {

            throw new DatabaseException("Failed to get all persons", e);
        }
    }

}
