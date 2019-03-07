package server.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import server.services.RegistrationService;
import shared.request.RegistrationRequest;
import shared.result.RegistrationResult;

import java.io.IOException;

public class RegisterHandler extends BaseHandler implements HttpHandler {

    public RegisterHandler() {

    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        int status = 200;
        RegistrationResult result;
        if (exchange.getRequestMethod().equalsIgnoreCase("POST")) {

            RegistrationRequest request = (RegistrationRequest) deserialize(
                exchange.getRequestBody(), RegistrationRequest.class
            );
            result = new RegistrationService().register(request);
            sendResponse(result, exchange, status);

        }

    }
}