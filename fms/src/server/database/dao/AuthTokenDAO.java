package server.database.dao;

import server.database.model.AuthToken;
import server.exceptions.DatabaseException;

import java.util.List;

public class AuthTokenDAO implements DAO<AuthToken> {

    @Override
    public void create() throws DatabaseException {

    }

    @Override
    public AuthToken get(String id) throws DatabaseException {
        return null;
    }

    @Override
    public List<AuthToken> getAll() throws DatabaseException {
        return null;
    }

    @Override
    public void remove(AuthToken model) throws DatabaseException {

    }

    @Override
    public void clear() throws DatabaseException {

    }
}