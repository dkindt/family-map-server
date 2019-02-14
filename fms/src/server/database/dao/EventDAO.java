package server.database.dao;

import server.database.model.Event;
import server.exceptions.DatabaseException;

import java.util.List;

public class EventDAO implements DAO<Event> {

    @Override
    public void create() throws DatabaseException {

    }

    @Override
    public Event get(String id) throws DatabaseException {
        return null;
    }

    @Override
    public List<Event> getAll() throws DatabaseException {
        return null;
    }

    @Override
    public void update(Event model, String[] params) throws DatabaseException {

    }

    @Override
    public void delete(Event model) throws DatabaseException {

    }

    @Override
    public void clear() throws DatabaseException {

    }
}