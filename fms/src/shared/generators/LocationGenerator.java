package shared.generators;

import server.database.model.Event;
import server.database.model.Locations;
import server.database.model.Locations.Location;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

import static shared.util.FileHelper.readJsonFile;

public class LocationGenerator {

    public static Event generate() {

        Event event = null;
        try {
            final Random random = new Random();
            final Path path = Paths.get("data/json", "locations.json");
            final Locations locations = (Locations) readJsonFile(
                path.toString(), Locations.class);

            // get a 'random' Location to create a base Event.
            int idx = random.nextInt(locations.totalLocations());
            Location location = locations.get(idx);

            event = new Event();
            event.setCountry(location.getCountry());
            event.setCity(location.getCity());
            event.setLatitude(location.getLatitude());
            event.setLongitude(location.getLongitude());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return event;
    }
}
