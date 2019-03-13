package server.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import server.exceptions.DatabaseException;
import server.services.RegistrationService;
import shared.request.RegistrationRequest;
import shared.result.RegistrationResult;

import java.io.IOException;

import static java.util.logging.Level.SEVERE;

public class RegisterHandler extends BaseHandler implements HttpHandler {

    public RegisterHandler() {
        this.supportedMethod = "POST";
    }

    @Override
    String getURLPattern() {
        return "(?i)^/user/register$";
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        int status = 200;
        RegistrationResult result = null;
        if (isValidRequestMethod(exchange)) {

            try {
                RegistrationRequest request = (RegistrationRequest) deserialize(
                    exchange.getRequestBody(), RegistrationRequest.class
                );
                result = new RegistrationService().register(request);
            } catch (DatabaseException e) {

                log.log(SEVERE, e.getMessage(), e);
                result = new RegistrationResult(e.getMessage());
                status = 500;
            }
        }
        sendJSONResponse(result, exchange, status);
    }
}