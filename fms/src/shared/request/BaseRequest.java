package shared.request;

import server.exceptions.InvalidParameterException;

import java.util.logging.Logger;

public abstract class BaseRequest {

    public abstract void verify() throws InvalidParameterException;
}
