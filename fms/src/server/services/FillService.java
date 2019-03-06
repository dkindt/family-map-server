package server.services;

import server.database.model.Locations;
import server.database.model.Names;
import shared.request.FillRequest;
import shared.result.FillResult;

import java.io.FileNotFoundException;

import static shared.util.FileHelper.readJsonFile;

/** Provides service for seeding database with data for a specific User. */
public class FillService {

    /**
     * Fills a User's pedigree from the FillRequest body.
     *
     * @param request FillRequest object.
     * @return FillResult object.
     */
    public FillResult fill(FillRequest request) throws FileNotFoundException {

        Locations locations = (Locations) readJsonFile("json/locations.json", Locations.class);
        Names females = (Names) readJsonFile("json/fnames.json", Names.class);
        Names males = (Names) readJsonFile("json/mnames.json", Names.class);
        Names surNames = (Names) readJsonFile("json/snames.json", Names.class);

        return null;
    }

}