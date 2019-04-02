package shared.generators;

import server.database.model.Names;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static shared.util.FileHelper.getAbsolutePath;
import static shared.util.FileHelper.readJsonFile;

public class NameGenerator {

    public enum NameType {
        FEMALE,
        MALE,
        SURNAME,
    }

    private Random random;
    private Map<NameType, Names> names;

    public NameGenerator() {

        try {
            random = new Random();
            names = new HashMap<>();
            addNames(NameType.FEMALE, "fnames.json");
            addNames(NameType.MALE, "mnames.json");
            addNames(NameType.SURNAME, "snames.json");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void addNames(NameType type, String fileName) throws FileNotFoundException {
        names.put(type, getNames(fileName));
    }

    private Names getNames(String fileName) throws FileNotFoundException {

        final String baseDir = getAbsolutePath("family-map/data/json");
        Path path = Paths.get(baseDir, fileName).toAbsolutePath();
        return (Names) readJsonFile(path.toString(), Names.class);
    }

    public String generateName(NameType type) {

        int idx = random.nextInt(names.get(type).totalNames());
        // String name = names.get(type).get(idx);
        // name = name.substring(1, name.length() - 1);
        return names.get(type).get(idx);
    }

}
