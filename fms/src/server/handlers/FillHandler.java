package server.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import server.exceptions.DatabaseException;
import server.services.FillService;
import shared.result.FillResult;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FillHandler extends BaseHandler implements HttpHandler {

    private static final int DEFAULT_NUM_GENERATIONS = 4;

    public FillHandler() {
        this.supportedMethod = "POST";
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        log.entering("FillHandler", "handle");

        if (isValidRequestMethod(exchange)) {

            boolean success = false;
            FillResult result;
            int status = 200;

            Map<String, String> params = parsePath(exchange.getRequestURI().getPath());
            String username = params.get("username");
            int generations = Integer.parseInt(params.get("generations"));

            try {
                result = new FillService().fill(username, generations);
                success = true;
            } catch (DatabaseException e) {
                result = new FillResult(e.getMessage());
                status = 500;
            }
            result.setSuccess(success);
            sendJSONResponse(result, exchange, status);
        }
    }

    private Map<String, String> parsePath(String path) {

        log.entering("FillHandler", "parsePath");

        final String pattern = "(?i)^/fill/(?<username>[^/]+/*)(?:(?<generations>[0-9]+))?$";
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(path);
        boolean matches = matcher.find();

        String generations = matcher.group("generations");
        if (generations == null) {
            generations = String.valueOf(DEFAULT_NUM_GENERATIONS);
        }

        Map<String, String> params = new HashMap<>();
        params.put("username", matcher.group("username"));
        params.put("generations", generations);
        return params;
    }
}