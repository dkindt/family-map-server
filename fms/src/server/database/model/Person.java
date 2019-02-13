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
        setUUID(uuid);
        setDescendant(descendant);
        setFirstName(firstName);
        setLastName(lastName);
        setGender(gender);
        setFather(father);
        setMother(mother);
        setSpouse(spouse);
    }

    public String getUUID() { return this.uuid; }
    public String getDescendant() { return this.descendant; }
    public String getFirstName() { return this.firstName; }
    public String getLastName() { return this.lastName; }
    public String getGender() { return this.gender; }
    public String getFather() { return this.father; }
    public String getMother() { return this.mother; }
    public String getSpouse() { return this.spouse; }

    public void setUUID(String uuid) {
        this.uuid = uuid;
    }

    public void setDescendant(String descendant) {
        this.descendant = descendant;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setFather(String father) {
        this.father = father;
    }

    public void setMother(String mother) {
        this.mother = mother;
    }

    public void setSpouse(String spouse) {
        this.spouse = spouse;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

}