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
    Event createModel(ResultSet resultSet) throws SQLException {
        Event event = new Event();
        event.setUUID(resultSet.getString("id"));
        event.setDescendant(resultSet.getString("descendant"));
        event.setPerson(resultSet.getString("person"));
        event.setLatitude(resultSet.getFloat("latitude"));
        event.setLongitude(resultSet.getFloat("longitude"));
        event.setCountry(resultSet.getString("country"));
        event.setCity(resultSet.getString("city"));
        event.setType(resultSet.getString("type"));
        event.setYear(resultSet.getInt("year"));
        return event;
    }

    @Override
    boolean create(Event model) throws DatabaseException {
        return false;
    }
}