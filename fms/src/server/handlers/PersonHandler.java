package server.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import server.database.model.Person;

import java.io.IOException;

public class PersonHandler extends BaseHandler implements HttpHandler {

    public PersonHandler() {
        this.supportedMethod = "GET";
        this.authRequired = true;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        if (isValidRequestMethod(exchange)) {

            String path = exchange.getRequestURI().getPath();

        }

    }

}
