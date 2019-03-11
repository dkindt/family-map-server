package server.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import server.exceptions.AuthenticationException;
import shared.request.RegistrationRequest;

import java.io.IOException;
import java.util.Map;

public class UserHandler extends BaseHandler implements HttpHandler {

    public UserHandler() {
        this.supportedMethod = "POST";
    }

    @Override
    boolean authorizationRequired() {
        return false;
    }

    @Override
    String getURLPattern() {
        return "(?i)^/user(?:/(?<action>(?:login|register))/*)?$";
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        if (isValidRequestMethod(exchange)) {

            try {
                Map<String, String> params = getURLParams(exchange);
                String action = params.get("actions");
                switch (action) {
                    case "login":
                        RegistrationRequest request = (RegistrationRequest) deserialize(
                            exchange.getRequestBody(), RegistrationRequest.class
                        );

                    case "register":
                        return;
                }
            } catch (AuthenticationException e) {
                log.severe(e.getMessage());
                e.printStackTrace();
            }

        }

    }
}