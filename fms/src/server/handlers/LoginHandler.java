package server.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import server.database.Database;
import server.exceptions.DatabaseException;
import server.services.LoginService;
import shared.request.LoginRequest;
import shared.result.LoginResult;

import java.io.IOException;
import java.util.logging.Logger;

public class LoginHandler extends BaseHandler implements HttpHandler {

    private static Logger log = Logger.getLogger("family-map-server");

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        if (isValidRequestMethod(exchange)) {

            try {
                LoginRequest request = (LoginRequest) deserialize(exchange.getRequestBody(), LoginRequest.class);
                LoginResult result = new LoginService().login(request);
                sendResponse(result, exchange, 200);
                exchange.close();
            } catch (DatabaseException e) {
                e.printStackTrace();
            }

        } else {
            log.severe("Invalid ");
        }

    }

    private boolean isValidRequestMethod(HttpExchange exchange) {
        final String HTTP_METHOD = "POST";
        return exchange.getRequestMethod().equalsIgnoreCase(HTTP_METHOD);
    }

}