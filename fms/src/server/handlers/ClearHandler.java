package server.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import server.services.ClearService;
import shared.result.ClearResult;

import java.io.IOException;

public class ClearHandler extends BaseHandler implements HttpHandler {

    public ClearHandler() {

    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        int status = 200;
        ClearResult result;
        if (exchange.getRequestMethod().equalsIgnoreCase("POST")) {

            ClearService service = new ClearService();
            result = service.clear();

        } else {
            status = 405;
            result = new ClearResult("Invalid method for clear()!");
        }
        sendResponse(result, exchange, status);
    }
}