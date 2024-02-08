package com.mechamate.service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Log {
    // Log levels
    public static enum LogLevelEnum {
        LogDebug,
        LogWarning,
        LogAlert,
        LogError,
        LogCritical
    }
    private boolean enable;     // Flag to indicate if logging is enabled
    private String logFile;     // Store the log file path
    private PrintWriter fileHandle;
    private LogCallback callback; // Store the optional callback function

    public Log(boolean enable, String filePath, LogCallback callback) {
        /**
         * Initializes the Log class with configuration options
         *
         * @param enable   True to enable logging, False to disable logging.
         * @param filePath Path to the Log file.
         * @param callback Optional function to be called with each log message.
         */
        this.enable = enable;
        this.logFile = filePath;
        this.callback = callback;
        this.enableLogging(enable);
    }

    public Log(boolean enable, String filePath) {
        /**
         * Constructor with a default callback of null.
         *
         * @param enable   True to enable logging, False to disable logging.
         * @param filePath Path to the Log file.
         */
        this(enable, filePath, null);
    }

    public void enableLogging(boolean enable) {
        /**
         * Enables or disables logging
         *
         * @param enable True to enable logging, False to disable
         */
        this.enable = enable;

        // Close the file handle if disabling logging
        if (!enable && fileHandle != null) {
            fileHandle.close();
            fileHandle = null;
        }
    }

    public void log(LogLevelEnum severity, String source, String message) {
        /**
         * Logs the message if logging is enabled.
         *
         * @param severity The severity as a LogLevelEnum value.
         * @param source   The source of the log message.
         * @param message  The content of the message
         */
        if (enable) {
            if (fileHandle == null) {
                // Open the log file in append mode if not already open
                try {
                    fileHandle = new PrintWriter(new FileWriter(logFile, true));
                    fileHandle.write("\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            // Format the log message using the severity name
            String formattedMessage = formatLog(severity.name(), source, message);

            // Write the message to the file and flush for immediate output
            fileHandle.write(formattedMessage + "\n");
            fileHandle.flush();
            // Call the optional callback function if needed
            if (callback != null) {
                callback.logCallback(formattedMessage);
            }
        }
    }

    private String formatLog(String logLevel, String source, String message) {
        /**
         * Formats the log message and returns it
         *
         * @param logLevel The log level ("Debug", "Warning", "Alert", "Error", "Critical")
         * @param source   The source of the message.
         * @param message  The content of the message.
         * @return The formatted log message
         */
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timestamp = dateFormat.format(new Date());
        return String.format("%s\t\t[%s] \t\t\t[%s] \t\t%s", timestamp, logLevel, source, message);
    }

    // Callback interface for optional function to be called
    public interface LogCallback {
        void logCallback(String message);
    }
    public static void main(String[] args) {
//         Example usage
        Log log = new Log(true, "src/test/log_records.txt", message -> {
            System.out.println("Callback: " + message);
        });

        log.log(LogLevelEnum.LogDebug, "source", "This is a debug message");
        log.log(LogLevelEnum.LogWarning, "source", "This is a warning message");
        log.log(LogLevelEnum.LogError, "source", "This is an error message");
    }


}

