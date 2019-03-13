package server.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import server.exceptions.AuthenticationException;
import server.exceptions.DatabaseException;
import server.services.EventService;
import shared.result.EventResult;

import java.io.IOException;
import java.util.Map;

public class EventHandler extends BaseHandler implements HttpHandler {

    public EventHandler() {
        this.supportedMethod = "GET";
    }

    @Override
    String getURLPattern() {
        return "(?i)^/event(?:/+(?<event>.+))?$";
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        log.entering("EventHandler", "handle");

        int status = 200;
        EventResult result;
        if (isValidRequestMethod(exchange)) {

            try {

                String token = getAuthorization(exchange);

                Map<String, String> params = getURLParams(exchange);
                String eventID = params.get("event");

                EventService service = new EventService();
                if (eventID == null) {
                    result = service.getAllEvents(token);
                } else {
                    result = service.getEvent(eventID, token);
                }


            } catch (AuthenticationException e) {

                log.severe(e.getMessage());
                result = new EventResult(e.getMessage());
                status = 401;

            } catch (DatabaseException e) {

                log.severe(e.getMessage());
                result = new EventResult(e.getMessage());
                status = 500;

            }
            sendJSONResponse(result, exchange, status);
        }
    }
}