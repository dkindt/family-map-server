package shared.request;

import server.exceptions.InvalidParameterException;

import java.util.logging.Logger;

public abstract class BaseRequest {

    protected static Logger log = Logger.getLogger("family-map-server");
    public abstract void verify() throws InvalidParameterException;
}
