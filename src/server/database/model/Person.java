package server.database.model;

public class Person {

    private String uuid;
    private String descendant;
    private String firstName;
    private String lastName;
    private String gender;
    private String father;
    private String mother;
    private String spouse;

    public Person(String uuid, String descendant, String firstName, String lastName,
                  String gender, String father, String mother, String spouse) {
        this.uuid = uuid;
        this.descendant = descendant;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.father = father;
        this.mother = mother;
        this.spouse = spouse;
    }

    public String getUUID() { return this.uuid; }
    public String getDescendant() { return this.descendant; }
    public String getFirstName() { return this.firstName; }
    public String getLastName() { return this.lastName; }
    public String getGender() { return this.gender; }
    public String getFather() { return this.father; }
    public String getMother() { return this.mother; }
    public String getSpouse() { return this.spouse; }
}