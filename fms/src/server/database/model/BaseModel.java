package server.database.model;

abstract class BaseModel {

    abstract public String toString();
    protected String toStringHelper(String field) {
        final String base = String.format("\n\t%s=", field);
        return base + "'%s'";
    }
}
