package server.handlers;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import server.services.FillService;
import shared.request.FillRequest;

import java.io.IOException;

public class FillHandler implements HttpHandler {

    public FillHandler() {

    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        if (exchange.getRequestMethod().equalsIgnoreCase("POST")) {

            Headers headers = exchange.getRequestHeaders();
            String path = exchange.getRequestURI().getPath();

            FillService service = new FillService();
            service.fill(new FillRequest());

        }

    }
}