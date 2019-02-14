package shared.result;

/** Represents response message or data to be returned to the Person service.  */
public class PersonResult {

    private String descendant;
    private String personID;
    private String firstName;
    private String lastName;
    private String gender;
    // these are optional and can be missing/null
    private String father;
    private String mother;
    private String spouse;

}
