package server.database.dao;

import server.database.model.Person;
import server.exceptions.DatabaseException;

import java.util.List;

public class PersonDAO implements DAO<Person> {
    @Override
    public void create() throws DatabaseException {

    }

    @Override
    public Person get(String id) throws DatabaseException {
        return null;
    }

    @Override
    public List<Person> getAll() throws DatabaseException {
        return null;
    }

    @Override
    public void remove(Person model) throws DatabaseException {

    }

    @Override
    public void clear() throws DatabaseException {

    }
}