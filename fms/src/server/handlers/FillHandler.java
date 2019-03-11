package server.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import server.exceptions.AuthenticationException;
import server.exceptions.DatabaseException;
import server.services.FillService;
import shared.result.FillResult;

import java.io.IOException;
import java.util.Map;


public class FillHandler extends BaseHandler implements HttpHandler {

    private static final int DEFAULT_NUM_GENERATIONS = 4;

    public FillHandler() {
        this.supportedMethod = "POST";
    }

    @Override
    boolean authorizationRequired() {
        return false;
    }

    @Override
    String getURLPattern() {
        return "(?i)^/fill/(?<username>[^/]+)(?:/(?<generations>[0-9]+))?$";
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        log.entering("FillHandler", "handle");

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
                    generations = DEFAULT_NUM_GENERATIONS;
                }

                result = new FillService().fill(username, generations);

            } catch (DatabaseException e) {
                result = new FillResult(e.getMessage());
                status = 500;
            } catch (AuthenticationException e) {
                result = new FillResult(e.getMessage());
                status = 401;
            }
            sendJSONResponse(result, exchange, status);
        }
    }
}