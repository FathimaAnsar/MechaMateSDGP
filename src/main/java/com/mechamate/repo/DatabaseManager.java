package com.mechamate.repo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;

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

        if (log == null) {
            throw new IllegalArgumentException("Log cannot be null");
        }
        this.log = log;

        if (hostName == null || hostName.trim().isEmpty()) {
            log.log(Log.LogLevelEnum.LogError, source, "Host name is null or empty");
            throw new IllegalArgumentException("Host name cannot be null or empty");
        }
        this.hostName = hostName;

        if (dbName == null || dbName.trim().isEmpty()) {
            log.log(Log.LogLevelEnum.LogError, source, "Database name is null or empty");
            throw new IllegalArgumentException("Database name cannot be null or empty");
        }
        this.dbName = dbName;

        if (username == null || username.trim().isEmpty()) {
            log.log(Log.LogLevelEnum.LogError, source, "Username is null or empty");
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        this.username = username;

        if (password == null || password.trim().isEmpty()) {
            log.log(Log.LogLevelEnum.LogError, source, "Password is null or empty");
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        this.password = password;

        this.isReady = false;

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
}