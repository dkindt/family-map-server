package server.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public class UserHandler extends BaseHandler implements HttpHandler {

    public UserHandler() {
        this.supportedMethod = "";
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

    }
}