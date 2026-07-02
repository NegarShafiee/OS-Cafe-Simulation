package loggerProcess;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class LoggerProcess {

    private static final int PORT = 5000;

    private static final String LOG_PATH =
            "logs/cafe.log";

    public static void main(String[] args) {

        System.out.println("[LOGGER PROCESS] Started.");

        try (
                ServerSocket serverSocket = new ServerSocket(PORT)
        ) {

            System.out.println(
                    "[LOGGER PROCESS] Waiting for connection..."
            );

            Socket socket = serverSocket.accept();

            System.out.println(
                    "[LOGGER PROCESS] Main process connected."
            );

            BufferedReader reader =
                    new BufferedReader(
                            new InputStreamReader(
                                    socket.getInputStream()
                            )
                    );

            File logFile = new File(LOG_PATH);

            File parent = logFile.getParentFile();

            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }

            BufferedWriter writer =
                    new BufferedWriter(
                            new FileWriter(
                                    logFile,
                                    true      // append
                            )
                    );

            String message;

            while ((message = reader.readLine()) != null) {

                // Console
                System.out.println(message);

                // File
                writer.write(message);
                writer.newLine();
                writer.flush();
            }

            writer.close();
            reader.close();
            socket.close();

        }

        catch (Exception e) {

            System.out.println(
                    "[LOGGER PROCESS] " +
                            e.getMessage()
            );
        }

        System.out.println(
                "[LOGGER PROCESS] Stopped."
        );
    }
}
