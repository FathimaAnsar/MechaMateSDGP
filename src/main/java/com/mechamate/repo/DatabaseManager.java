package com.mechamate.repo;

import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import io.github.cdimascio.dotenv.Dotenv;

import java.util.Collections;

public class DatabaseManager {

    private MongoClient mongoClient;
    private MongoDatabase database;
    private String hostName;
    private String dbName;
    private String username;
    private String password;
    private Log log;
    private Cache cache;
    private boolean isReady;

    public DatabaseManager(String hostName, String dbName, String username, String password, Log log) {
        String source = this.getClass().getSimpleName() + "::DatabaseManager";
        this.isReady = false;

        if (log == null) {
            throw new IllegalArgumentException("Log cannot be null");
        }
        this.log = log;

        if (hostName == null || hostName.trim().isEmpty()) {
            log.log(Log.LogLevelEnum.LogError, source, "Host name is null or empty");
        }
        this.hostName = hostName;

        if (dbName == null || dbName.trim().isEmpty()) {
            log.log(Log.LogLevelEnum.LogError, source, "Database name is null or empty");
        }
        this.dbName = dbName;

        if (username == null || username.trim().isEmpty()) {
            log.log(Log.LogLevelEnum.LogError, source, "Username is null or empty");
        }
        this.username = username;

        if (password == null || password.trim().isEmpty()) {
            log.log(Log.LogLevelEnum.LogError, source, "Password is null or empty");
        }
        this.password = password;

        try {
            // initialize cache instance
            this.cache = new Cache(log);
            this.isReady = true;
            log.log(Log.LogLevelEnum.LogDebug, source, "DatabaseManager initialized successfully");
        } catch (Exception e) {
            log.log(Log.LogLevelEnum.LogError, source, "Failed to initialize Cache: " + e.getMessage());
            throw new IllegalStateException("Failed to initialize Cache", e);
        }
    }
    public boolean connectToDb() {
        String source = this.getClass().getSimpleName() + "::connectToDb";

        if (isReady){
            log.log(Log.LogLevelEnum.LogDebug, source, "DatabaseManager is ready");
            try {
                MongoCredential credential = MongoCredential.createCredential(username, dbName, password.toCharArray());
                MongoClientSettings settings = MongoClientSettings.builder()
                        .applyToClusterSettings(builder -> builder.hosts(Collections.singletonList(new ServerAddress(hostName))))
                        .credential(credential)
                        .build();
                mongoClient = MongoClients.create(settings);
                database = mongoClient.getDatabase(dbName);
                log.log(Log.LogLevelEnum.LogDebug, source, "DatabaseManager connected successfully");
                return true;
            } catch (Exception e) {
                log.log(Log.LogLevelEnum.LogError, source, "Could not connect to the database: " + e.getMessage());
                return false;
            }
        } else {
            log.log(Log.LogLevelEnum.LogCritical, source, "DatabaseManager is not ready");
            return false;
        }
    }
    public boolean disconnectFromDb() {
        String source = this.getClass().getSimpleName() + "::disconnectFromDb";
        if (mongoClient != null) {
            try {
                mongoClient.close();
                log.log(Log.LogLevelEnum.LogDebug, source, "Disconnected from the database successfully");
                return true;
            } catch (Exception e) {
                log.log(Log.LogLevelEnum.LogError, source, "Error occurred while disconnecting the database: " + e.getMessage());
                return false;
            }
        } else {
            log.log(Log.LogLevelEnum.LogCritical, source, "No database connection was established");
            return true; // No database to disconnect from
        }
    }
    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load();
        String hostName = dotenv.get("DB_HOSTNAME");
        String dbName = dotenv.get("DB_NAME");
        String username = dotenv.get("DB_USERNAME");
        String password = dotenv.get("DB_PASSWORD");
        Log log = new Log(true, "src/test/log_records.txt", message -> {
            System.out.println("Callback: " + message);
        });

        if (hostName != null && dbName != null && username != null && password != null) {
            DatabaseManager dbManager = new DatabaseManager(hostName, dbName, username, password, log);
            if (dbManager.connectToDb()) {
                dbManager.disconnectFromDb();
            }
        } else {
            System.out.println("env variables are not set.");
        }
    }
}
