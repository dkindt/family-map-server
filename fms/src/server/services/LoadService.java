package server.services;

import shared.request.LoadRequest;
import shared.result.LoadResult;

/** Provides service for clearing and loading custom data into the database. */
public class LoadService {

    /**
     * Clears all data from the database (just like the `/clear` API), and then
     * loads the posted user, person, and event data into the database.
     *
     * @param request payload containing lists of user, person, and event objects.
     * @return LoadResult object with message success/error message(s).
     */
    public LoadResult load(LoadRequest request) {
        return null;
    }

}