package server.database.dao;

import server.database.model.Event;
import server.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EventDAO extends DAO<Event> {

    public EventDAO(Connection connection) {
        super(connection, "events", "id");
    }

    @Override
    Event modelFromResultSet(ResultSet resultSet) throws SQLException {
        return new Event(resultSet);
    }

    @Override
    boolean create(Event model) throws DatabaseException {
        return false;
    }
}