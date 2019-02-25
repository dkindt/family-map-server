package server.database.dao;

import server.database.model.Person;
import server.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonDAO extends DAO<Person> {

    public PersonDAO(Connection connection) {

        super(connection, "persons", "uuid");
    }

    @Override
    Person modelFromResultSet(ResultSet resultSet) throws SQLException {
        return new Person(resultSet);
    }

    @Override
    public boolean create(Person person) throws DatabaseException {
        String sql = "INSERT INTO " + tableName + " VALUES (?,?,?,?,?,?,?,?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            //statement.setString(1, tableName);
            statement.setString(1, person.getUUID());
            statement.setString(2, person.getDescendant());
            statement.setString(3, person.getFirstName());
            statement.setString(4, person.getLastName());
            statement.setString(5, person.getGender());
            statement.setString(6, person.getFather());
            statement.setString(7, person.getMother());
            statement.setString(8, person.getSpouse());
            int rows = statement.executeUpdate();
            if (rows == 1) return true;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException(
                String.format("Failed to create(): %s", person.toString()));
        }
        return false;
    }
}