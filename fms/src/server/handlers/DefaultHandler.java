package server.handlers;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public class DefaultHandler implements HttpHandler {

    private final static String ROOT_DIR = "web";
    private final static String DEFAULT_PAGE = "index.html";
    private final static String NOT_FOUND_PAGE = "404.html";

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        /// 1. Retrieve the request URL from the HttpExchange
        //	2. Translate the request URL path to a physical file path on your server
        //	3. Open the requested file, and return its contents in the HTTP
        //	    response body
        try {
            if (isValidRequestMethod(exchange)) {

                // File file = getFile(exchange.getRequestURI());
                serveFile(exchange);

            } else {
                // unsupported http method
            }
        } catch (IOException e) {

        }
    }

    private boolean isValidRequestMethod(HttpExchange exchange) {
        String VALID_HTTP_METHOD = "GET";
        return exchange.getRequestMethod().equalsIgnoreCase(VALID_HTTP_METHOD);
    }

    private Path buildFilePath(URI uri) {

        FileSystem fileSystem = FileSystems.getDefault();
        String path = uri.getPath();
        if (path.equals("/")) {
            path = String.format("/%s", DEFAULT_PAGE);
        }
        if (!isValidFilePath(path)) {
            path = String.format("/%s", NOT_FOUND_PAGE);
        }
        return fileSystem.getPath(ROOT_DIR, path);
    }

    private boolean isValidFilePath(String filePath) {
        Path path = Path.of(filePath);
        return Files.exists(path) && Files.isReadable(path);
    }

    private void serveFile(HttpExchange exchange) throws IOException {

        // Build the path to the file and determine it's content-type
        Path path = buildFilePath(exchange.getRequestURI());
        String ext = Files.probeContentType(path);
        String contentType = "";

        File file = path.toFile();

    }

    private void sendResponse(
        HttpExchange exchange,
        InputStream stream,
        int code,
        long length) {

        Headers headers = exchange.getResponseHeaders();
        headers.add("Server", "FMS");
        headers.add("Connection", "close");
        headers.add("Content-Length", String.valueOf(length));



    }

}
