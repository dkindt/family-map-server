package server.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import server.services.ClearService;
import shared.result.ClearResult;

import java.io.IOException;

public class ClearHandler extends BaseHandler implements HttpHandler {

    public ClearHandler() {
        this.supportedMethod = "POST";
    }

    @Override
    String getURLPattern() {
        return "(?i)^/clear/*$";
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        log.entering("ClearHandler", "handle");

        int status = 200;
        ClearResult result;
        if (isValidRequestMethod(exchange)) {

            ClearService service = new ClearService();
            result = service.clear();

        } else {
            status = 405;
            result = new ClearResult("Invalid method for clear()!");
        }
        sendJSONResponse(result, exchange, status);
    }
}