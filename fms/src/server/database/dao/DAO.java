package server.database.dao;

import server.exceptions.DatabaseException;

import java.util.List;

public interface DAO<T> {

    void create() throws DatabaseException;

    T get(String id) throws DatabaseException;

    List<T> getAll() throws DatabaseException;

    void remove(T model) throws DatabaseException;

    void clear() throws DatabaseException;
}
