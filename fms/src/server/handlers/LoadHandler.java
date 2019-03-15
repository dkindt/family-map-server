package server.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import server.services.LoadService;
import shared.request.LoadRequest;
import shared.result.LoadResult;

import java.io.IOException;

public class LoadHandler extends BaseHandler implements HttpHandler {

    public LoadHandler() {
        this.supportedMethod = "POST";
    }

    @Override
    String getURLPattern() {
        return "(?i)^/load/*$";
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        int status = 500;
        LoadResult result = null;
        if (isValidRequestMethod(exchange)) {

            LoadRequest request = (LoadRequest) deserialize(
                exchange.getRequestBody(), LoadRequest.class
            );
            result = new LoadService().load(request);
        }
        sendJSONResponse(result, exchange, status);
    }
}
