package server.database.model;

/** Represents a Person row in the persons table. */
public class Person {

    private String uuid;
    private String descendant;
    private String firstName;
    private String lastName;
    private String gender;
    private String father;
    private String mother;
    private String spouse;

    /**
     * Generates a single Person
     * @param uuid Unique identifier for the person
     * @param descendant User (username) to which person belongs
     * @param firstName Person's first name
     * @param lastName Person's last name
     * @param gender Person's gender ("f" or "m")
     * @param father personID for the father (optional)
     * @param mother personID for the mother (optional)
     * @param spouse personID for the spouse (optional)
     */
    public Person(String uuid, String descendant, String firstName, String lastName,
                  String gender, String father, String mother, String spouse) {
        setUUID(uuid);
        setDescendant(descendant);
        setFirstName(firstName);
        setLastName(lastName);
        setGender(gender);
        setFather(father);
        setMother(mother);
        setSpouse(spouse);
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
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

}