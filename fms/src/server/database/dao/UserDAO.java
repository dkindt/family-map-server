package server.database.dao;

import server.database.model.User;
import server.exceptions.DatabaseException;

import java.util.List;

public class UserDAO implements DAO<User> {
    @Override
    public void create() throws DatabaseException {

    }

    @Override
    public User get(String id) throws DatabaseException {
        return null;
    }

    @Override
    public List<User> getAll() throws DatabaseException {
        return null;
    }

    @Override
    public void remove(User model) throws DatabaseException {

    }

    @Override
    public void clear() throws DatabaseException {

    }
}