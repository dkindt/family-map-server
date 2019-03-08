package server.database.model;

import java.util.Arrays;

public class Names {

    private String[] data;

    public Names(String[] data) {
        this.data = data;
    }

    public String get(int idx) {
        return data[idx];
    }

    public int totalNames() {
        return data.length;
    }

    @Override
    public String toString() {
        return Arrays.toString(data);
    }
}
