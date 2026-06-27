package logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {

    private static final String LOG_DIRECTORY = "logs";
    private static final String LOG_FILE = "logs/cafe.log";

    private static final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static BufferedWriter writer;

    static {

        try {

            File directory = new File(LOG_DIRECTORY);

            if (!directory.exists()) {
                directory.mkdirs();
            }

            writer = new BufferedWriter(
                    new FileWriter(LOG_FILE, true) // true: new logs append to old logs
                                                          // false: start with clear log file
            );

        } catch (IOException e) {

            System.err.println("Logger initialization failed.");

            e.printStackTrace();
        }
    }

    private static synchronized void log(
            LogLevel level,
            String message
    ) {

        String time =
                LocalDateTime.now().format(formatter);

        String logMessage =
                String.format(
                        "%s [%s] %s",
                        time,
                        level,
                        message
                );

        try {

            System.out.println(logMessage);

            writer.write(logMessage);
            writer.newLine();
            writer.flush();

        } catch (IOException e) {

            e.printStackTrace();

        }

    }

    public static void info(String message) {
        log(LogLevel.INFO, message);
    }

    public static void warning(String message) {
        log(LogLevel.WARNING, message);
    }

    public static void error(String message) {
        log(LogLevel.ERROR, message);
    }

    public static void close() {

        try {

            if (writer != null) {
                writer.close();
            }

        } catch (IOException e) {

            e.printStackTrace();
        }
    }
}
