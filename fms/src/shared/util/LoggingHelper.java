package shared.util;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.*;

public class LoggingHelper {

    public static Logger setupLogger(String name) {

        Logger logger = Logger.getLogger(name);

        Level level = Level.FINEST;

        logger.setLevel(level);
        logger.setUseParentHandlers(false);

        Handler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(level);
        consoleHandler.setFormatter(new LogFormatter());
        logger.addHandler(consoleHandler);

        try {
            String fileName = String.format("%s.log", name);
            FileHandler fileHandler = new FileHandler(("%t" + fileName), false);
            fileHandler.setLevel(level);
            fileHandler.setFormatter(new LogFormatter());
            logger.addHandler(fileHandler);
        } catch (IOException e) {
            logger.severe("Unable to initialize FileHandler for logger!");
        }
        return logger;
    }

    public static class LogFormatter extends Formatter {

        // Create a DateFormat to format the logger timestamp.
        private final String datePattern = "dd/MM/yyyy hh:mm:ss.SSS";
        private final DateFormat df = new SimpleDateFormat(datePattern);

        public String getHead(Handler handler) {
            return super.getHead(handler);
        }

        public String getTail(Handler handler) {
            return super.getTail(handler);
        }

        @Override
        public String format(LogRecord record) {

            return String.format(
                "%s - [%s.%s] - [%s] - %s%n",
                df.format(new Date(record.getMillis())),
                record.getSourceClassName(),
                record.getSourceMethodName(),
                record.getLevel(),
                formatMessage(record)
            );
        }
    }
}
