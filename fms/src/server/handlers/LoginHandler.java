package server.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import server.exceptions.DatabaseException;
import server.services.LoginService;
import shared.request.LoginRequest;
import shared.result.LoginResult;

import java.io.IOException;

public class LoginHandler extends BaseHandler implements HttpHandler {

    public LoginHandler() {
        this.supportedMethod = "POST";
    }

    @Override
    String getURLPattern() {
        return "(?i)^/user/login/*$";
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        log.entering("LoginHandler", "handle");

        int status = 200;
        LoginResult result = null;
        if (isValidRequestMethod(exchange)) {

            try {
                LoginRequest request = (LoginRequest) deserialize(
                    exchange.getRequestBody(), LoginRequest.class
                );
                result = new LoginService().login(request);

            } catch (DatabaseException e) {

                log.severe(e.getMessage());
                result = new LoginResult(e.getMessage());
                status = 500;
            }
        }
        sendJSONResponse(result, exchange, status);
    }

}