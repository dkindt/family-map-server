package server.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import static shared.util.FileHelper.inputStreamToString;

abstract class BaseHandler {

    public BaseHandler() {}

    protected void sendResponse(Object result, HttpExchange exchange, int code) throws IOException {

        exchange.sendResponseHeaders(code, 0);
        OutputStream body = exchange.getResponseBody();
        OutputStreamWriter writer = new OutputStreamWriter(body);
        writer.write(serialize(result));
        writer.flush();

    }

    protected Object deserialize(InputStream input, Class klass) throws IOException {
        return new Gson().fromJson(
            // TODO: is this the best way to handle it?
            inputStreamToString(input).toLowerCase(),
            klass
        );
    }

    protected String serialize(Object o) {
        return new Gson().toJson(o, o.getClass());
    }

}
