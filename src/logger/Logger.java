package logger;

public class Logger {

    public static synchronized void log(String message) {

        System.out.println(message);

        // TODO: save in .log file
    }
}