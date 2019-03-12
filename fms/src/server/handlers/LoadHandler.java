package server.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import server.exceptions.DatabaseException;
import server.exceptions.InvalidParameterException;
import server.services.LoadService;
import shared.request.LoadRequest;
import shared.result.LoadResult;

import java.io.IOException;

import static java.lang.String.format;

public class LoadHandler extends BaseHandler implements HttpHandler {

    public LoadHandler() {
        this.supportedMethod = "POST";
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        log.entering("LoadHandler", "handle");

        int status = 500;
        LoadResult result = null;

        try {
            if (isValidRequestMethod(exchange)) {

                LoadRequest request = (LoadRequest) deserialize(
                    exchange.getRequestBody(), LoadRequest.class
                );
                result = new LoadService().load(request);
            }
        } catch (InvalidParameterException e) {
            String msg = format(
                "InvalidParameterException(param='%s', desc='%s')",
                e.getMessage(), e.getParameter()
            );
            log.info(msg);
            result = new LoadResult(msg);
            status = 400;
        }
        sendJSONResponse(result, exchange, status);
    }

    @Override
    String getURLPattern() {
        return "(?i)^/load/*$";
    }
}
