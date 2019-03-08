package server.database.model;

import static shared.util.DatabaseHelper.generateUUID;

/** Represents a Person row in the persons table. */
public class Person extends BaseModel {

    private String uuid;
    private String descendant;
    private String firstName;
    private String lastName;
    private String gender;
    private String father;
    private String mother;
    private String spouse;

    public Person() {
        this.uuid = generateUUID();
    }

    public String getUUID() {
        return uuid;
    }

    public void setUUID(String uuid) {
        this.uuid = uuid;
    }

    public String getDescendant() {
        return descendant;
    }

    public void setDescendant(String descendant) {
        this.descendant = descendant;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFather() {
        return father;
    }

    public void setFather(String father) {
        this.father = father;
    }

    public String getMother() {
        return mother;
    }

    public void setMother(String mother) {
        this.mother = mother;
    }

    public String getSpouse() {
        return spouse;
    }

    public void setSpouse(String spouse) {
        this.spouse = spouse;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("\nPerson(");
        sb.append(toStringHelper("uuid"));
        sb.append(toStringHelper("descendant"));
        sb.append(toStringHelper("firstName"));
        sb.append(toStringHelper("lastName"));
        sb.append(toStringHelper("gender"));
        sb.append(toStringHelper("father"));
        sb.append(toStringHelper("mother"));
        sb.append(toStringHelper("spouse"));
        sb.append("\n)");
        String format = sb.toString();
        return String.format(
            format,
            getUUID(),
            getDescendant(),
            getFirstName(),
            getLastName(),
            getGender(),
            getFather(),
            getMother(),
            getSpouse()
        );
    }
}