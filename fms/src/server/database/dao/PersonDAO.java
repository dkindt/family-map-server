package server.database.dao;

import server.database.model.Person;
import server.exceptions.AuthenticationException;
import server.exceptions.DatabaseException;

import java.io.IOException;
import java.sql.*;

import static java.lang.String.format;
import static shared.util.FileHelper.getAbsolutePath;
import static shared.util.FileHelper.loadFile;

public class PersonDAO extends DAO<Person> {

    public PersonDAO(Connection connection) {

        super(connection, "persons", "personID");
    }

    @Override
    Person modelFactory(ResultSet resultSet) throws SQLException {
        Person person = new Person();
        person.setUUID(resultSet.getString("personID"));
        person.setDescendant(resultSet.getString("descendant"));
        person.setFirstName(resultSet.getString("first_name"));
        person.setLastName(resultSet.getString("last_name"));
        person.setGender(resultSet.getString("gender"));
        person.setFather(resultSet.getString("father"));
        person.setMother(resultSet.getString("mother"));
        person.setSpouse(resultSet.getString("spouse"));
        return person;
    }

    @Override
    int getNumColumns() {
        return Person.class.getDeclaredFields().length;
    }

    @Override
    void bindParameters(PreparedStatement statement, Person person) throws SQLException {

        statement.setString(1, person.getUUID());
        statement.setString(2, person.getDescendant());
        statement.setString(3, person.getFirstName());
        statement.setString(4, person.getLastName());
        statement.setString(5, person.getGender());
        statement.setString(6, person.getFather());
        statement.setString(7, person.getMother());
        statement.setString(8, person.getSpouse());
    }

    public Person getFromAuth(String personID, String token)
        throws AuthenticationException, DatabaseException {

        try {

            final String sql = loadFile(getAbsolutePath("sql/person_from_auth.sql"));
            try (PreparedStatement statement = connection.prepareStatement(sql)) {

                statement.setString(1, personID);
                statement.setString(2, token);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    return modelFactory(resultSet);
                }
                throw new AuthenticationException();

            } catch (SQLException e) {
                throw new DatabaseException(
                    format("Failed to get Person w/token='%s'", token), e);
            }

        } catch (IOException e) {

            throw new DatabaseException("Failed to load SQL file!");
        }
    }

}