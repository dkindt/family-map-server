package server.database.dao;

import server.database.model.Event;
import server.exceptions.AuthenticationException;
import server.exceptions.DatabaseException;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static java.lang.String.format;
import static shared.util.FileHelper.getAbsolutePath;
import static shared.util.FileHelper.loadFile;

public class EventDAO extends DAO<Event> {

    public EventDAO(Connection connection) {
        super(connection, "events", "eventID");
    }

    @Override
    Event modelFactory(ResultSet resultSet) throws SQLException {
        Event event = new Event();
        event.setEventID(resultSet.getString("eventID"));
        event.setDescendant(resultSet.getString("descendant"));
        event.setPerson(resultSet.getString("personID"));
        event.setLatitude(resultSet.getFloat("latitude"));
        event.setLongitude(resultSet.getFloat("longitude"));
        event.setCountry(resultSet.getString("country"));
        event.setCity(resultSet.getString("city"));
        event.setEventType(resultSet.getString("eventType"));
        event.setYear(resultSet.getInt("year"));
        return event;
    }

    @Override
    int getNumColumns() {
        return Event.class.getDeclaredFields().length;
    }

    @Override
    void bindParameters(PreparedStatement statement, Event event) throws SQLException {

        statement.setString(1, event.getEventID());
        statement.setString(2, event.getDescendant());
        statement.setString(3, event.getPerson());
        statement.setFloat(4, event.getLatitude());
        statement.setFloat(5, event.getLongitude());
        statement.setString(6, event.getCountry());
        statement.setString(7, event.getCity());
        statement.setString(8, event.getEventType());
        statement.setInt(9, event.getYear());
    }

    public Event getFromAuth(String eventID, String token) throws AuthenticationException, DatabaseException {

        try {

            final String sql = loadFile(getAbsolutePath("sql/event_from_auth.sql"));
            try (PreparedStatement statement = connection.prepareStatement(sql)) {

                statement.setString(1, eventID);
                statement.setString(2, token);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) return modelFactory(resultSet);
                else throw new AuthenticationException();

            } catch (SQLException e) {

                throw new DatabaseException(
                    format("Failed to get Event w/token='%s'", token), e);
            }
        } catch (IOException e) {

            throw new DatabaseException("Failed to load SQL file!");
        }
    }
}