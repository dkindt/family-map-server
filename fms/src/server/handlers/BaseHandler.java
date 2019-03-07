package server.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.EnumMap;
import java.util.logging.Logger;

import static shared.util.FileHelper.inputStreamToString;

abstract class BaseHandler {

    // TODO: this needs to be updated in concrete classes.
    protected String supportedMethod;
    protected static Logger log = Logger.getLogger("family-map-server");

    public enum HTTP_STATUS {
        INVALID_REQUEST_METHOD,
        INTERNAL_SERVER_ERROR,
        SUCCESS,
    }

    protected EnumMap<HTTP_STATUS, Integer> statusCodes = new EnumMap<>(HTTP_STATUS.class);

    public BaseHandler() {}

    protected void sendResponse(Object result, HttpExchange exchange, int code) throws IOException {

        exchange.sendResponseHeaders(code, 0);
        OutputStream body = exchange.getResponseBody();
        OutputStreamWriter writer = new OutputStreamWriter(body);
        writer.write(serialize(result));
        writer.flush();
        exchange.close();

    }

    protected Object deserialize(InputStream input, Class klass) throws IOException {
        return new Gson().fromJson(
            // TODO: is this the best way to handle it?
            inputStreamToString(input).replace("userName", "username"),
            klass
        );
    }

    protected String serialize(Object o) {
        return new Gson().toJson(o, o.getClass());
    }

    protected boolean isValidRequestMethod(HttpExchange exchange) {
        final String HTTP_METHOD = "POST";
        return exchange.getRequestMethod().equalsIgnoreCase(HTTP_METHOD);
    }

}
