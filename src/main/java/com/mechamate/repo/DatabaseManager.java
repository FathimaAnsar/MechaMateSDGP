package com.mechamate.MechaMate.repo;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DatabaseManager implements DisposableBean {

    private MongoClient mongoClient;
    private MongoDatabase database;

    // injecting values from application.properties file
    @Value("${spring.data.mongodb.uri}")
    private String mongoDbUri;

    @Value("${spring.data.mongodb.database}")
    private String dbName;

    // @PostConstruct is used to execute the code after the bean instantiation and dependency injection.
    @PostConstruct
    public void startDatabase() {
        mongoClient = MongoClients.create(mongoDbUri);
        database = mongoClient.getDatabase(dbName);
        System.out.println("Connected to MongoDB database: " + database.getName());
    }

    @Override
    public void destroy() {
        if (mongoClient != null) {
            mongoClient.close();
            System.out.println("Disconnected from MongoDB database");
        }
    }

}
