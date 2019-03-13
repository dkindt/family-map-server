package server.handlers;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.lang.String.format;

public class FileHandler extends BaseHandler implements HttpHandler {

    private final static String ROOT_DIR = "web";
    private final static String DEFAULT_PAGE = "index.html";
    private final static String NOT_FOUND_PAGE = "html/404.html";

    public FileHandler() {
        this.supportedMethod = "GET";
    }

    @Override
    String getURLPattern() {
        final String pattern = "";
        return pattern;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        int status = 200;
        if (isValidRequestMethod(exchange)) {

            Path path = buildFilePath(exchange.getRequestURI());
            File file = path.toFile();
            sendResponse(exchange, new FileInputStream(file), status);
        }
    }

    private Path buildFilePath(URI uri) {

        FileSystem fs = FileSystems.getDefault();
        String path = uri.getPath();
        if (path.equals("/")) {
            path = format("%s", DEFAULT_PAGE);
        }
        Path fullPath = fs.getPath(ROOT_DIR, path);
        if (!isValidFilePath(fullPath)) {
            fullPath = fs.getPath(ROOT_DIR, NOT_FOUND_PAGE);
        }
        return fullPath;
    }

    private boolean isValidFilePath(Path path) {
        return Files.exists(path) && Files.isReadable(path);
    }

}
