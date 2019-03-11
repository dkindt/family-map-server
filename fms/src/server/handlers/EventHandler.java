package server.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public class EventHandler extends BaseHandler implements HttpHandler {

    public EventHandler() {
        this.supportedMethod = "GET";
        this.authRequired = true;
    }

    @Override
    boolean authorizationRequired() {
        return false;
    }

    @Override
    String getURLPattern() {
        final String pattern = "";
        return pattern;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

    }
}