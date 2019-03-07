package server.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import server.exceptions.DatabaseException;
import server.services.LoginService;
import shared.request.LoginRequest;
import shared.result.LoginResult;

import java.io.IOException;

public class LoginHandler extends BaseHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        int status = 200;
        LoginResult result;
        if (isValidRequestMethod(exchange)) {

            try {
                LoginRequest request = (LoginRequest) deserialize(
                    exchange.getRequestBody(), LoginRequest.class
                );
                result = new LoginService().login(request);
                sendResponse(result, exchange, status);
            } catch (DatabaseException e) {
                e.printStackTrace();
            }

        } else {
            log.severe("Unsupported method");
        }
    }

}