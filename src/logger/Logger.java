package logger;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {

    private static final String HOST = "localhost";
    private static final int PORT = 5000;

    private static Socket socket;
    private static BufferedWriter writer;

    private static final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void connect() {

        try {

            socket = new Socket(HOST, PORT);

            writer = new BufferedWriter(
                    new OutputStreamWriter(
                            socket.getOutputStream()
                    )
            );

            System.out.println("[LOGGER] Connected to Logger Process.");

        } catch (IOException e) {

            System.out.println(
                    "[LOGGER] Could not connect to Logger Process."
            );

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

        // Console
        System.out.println(logMessage);

        // Logger Process
        if (writer != null) {

            try {

                writer.write(logMessage);
                writer.newLine();
                writer.flush();

            } catch (IOException e) {

                System.out.println(
                        "[LOGGER] Failed to send log message."
                );

            }

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

            if (socket != null) {
                socket.close();
            }

        } catch (IOException e) {

            e.printStackTrace();

        }

    }

}
