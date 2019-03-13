package server.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import server.exceptions.DatabaseException;
import server.services.FillService;
import shared.result.FillResult;

import java.io.IOException;
import java.util.Map;

import static java.util.logging.Level.SEVERE;
import static shared.generators.FamilyTreeGenerator.DEFAULT_GENERATIONS;


public class FillHandler extends BaseHandler implements HttpHandler {

    public FillHandler() {
        this.supportedMethod = "POST";
    }

    @Override
    String getURLPattern() {
        return "(?i)^/fill/(?<username>[^/]+)(?:/(?<generations>[0-9]+))?$";
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        if (isValidRequestMethod(exchange)) {

            FillResult result;
            int status = 200;

            try {
                Map<String, String> params = getURLParams(exchange);

                String username = params.get("username");
                String gens = params.get("generations");
                int generations = 0;
                if (gens != null) {
                    generations = Integer.parseInt(gens);
                }
                if (generations <= 0) {
                    generations = DEFAULT_GENERATIONS;
                }

                result = new FillService().fill(username, generations);

            } catch (DatabaseException e) {

                log.log(SEVERE, e.getMessage(), e);
                result = new FillResult(e.getMessage());
                status = 500;
            }
            sendJSONResponse(result, exchange, status);
        }
    }
}