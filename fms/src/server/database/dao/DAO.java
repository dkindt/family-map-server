package server.database.dao;

import server.exceptions.DatabaseException;

import java.util.List;

/**
 * Provides the base DAO interface for each service.
 *
 * @param <T> Model object that will implement the interface.
 */
public interface DAO<T> {

    void create() throws DatabaseException;

    T get(String id) throws DatabaseException;

    List<T> getAll() throws DatabaseException;

    void update(T model, String[] params) throws DatabaseException;

    void delete(T model) throws DatabaseException;

    void clear() throws DatabaseException;

}
