package server.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import server.database.model.Person;
import server.exceptions.AuthenticationException;

import java.io.IOException;
import java.util.Map;

public class PersonHandler extends BaseHandler implements HttpHandler {

    public PersonHandler() {
        this.supportedMethod = "GET";
    }

    @Override
    boolean authorizationRequired() {
        return true;
    }

    @Override
    String getURLPattern() {
        return "";
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        if (isValidRequestMethod(exchange)) {

            try {

                Map<String, String> params = getURLParams(exchange);

            } catch (AuthenticationException e) {

                e.printStackTrace();
            }
        }
    }
}
