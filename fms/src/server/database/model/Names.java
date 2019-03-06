package server.database.model;

import java.util.Arrays;

public class Names {

    private String[] data;

    public Names(String[] data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return Arrays.toString(data);
    }
}
