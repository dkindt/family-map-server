package server.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import server.exceptions.AuthenticationException;
import server.exceptions.DatabaseException;
import server.services.PersonService;
import shared.result.PersonResult;

import java.io.IOException;
import java.util.Map;

public class PersonHandler extends BaseHandler implements HttpHandler {

    public PersonHandler() {
        this.supportedMethod = "GET";
    }

    @Override
    String getURLPattern() {
        return "(?i)^/person(?:/+(?<person>.+))?$";
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        log.entering("PersonHandler", "handle");

        int status = 200;
        PersonResult result;
        if (isValidRequestMethod(exchange)) {

            try {

                String token = getAuthorization(exchange);

                Map<String, String> params = getURLParams(exchange);
                String personID = params.get("person");

                PersonService service = new PersonService();
                if (personID == null) result = service.getAllPersons(token);
                else result = service.getPerson(personID, token);

            } catch (AuthenticationException e) {

                log.severe(e.getMessage());
                result = new PersonResult(e.getMessage());
                status = 401;

            } catch (DatabaseException e) {

                log.severe(e.getMessage());
                result = new PersonResult(e.getMessage());
                status = 500;

            }
            sendJSONResponse(result, exchange, status);
        }
    }
}
