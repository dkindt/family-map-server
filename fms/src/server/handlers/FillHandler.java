package server.handlers;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import server.exceptions.DatabaseException;
import server.services.FillService;
import shared.request.FillRequest;
import shared.result.FillResult;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FillHandler extends BaseHandler implements HttpHandler {

    public FillHandler() {

    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        if (exchange.getRequestMethod().equalsIgnoreCase("POST")) {

            int status = 200;

            Map<String, String> params = parsePath(exchange.getRequestURI().getPath());
            String username = params.get("username");
            int generations = Integer.parseInt(params.get("generations"));

            try {

                FillResult result = new FillService().fill(username, generations);
                sendResponse(result, exchange, status);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private Map<String, String> parsePath(String path) {

        final String pattern = "(?i)^/fill/(?<username>[^/]+)(?:/(?<generations>[0-9]+))?$";
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(path);
        boolean matches = matcher.find();
        return new HashMap<>(){{
            put("username", matcher.group("username"));
            put("generations", matcher.group("generations"));
        }};
    }
}