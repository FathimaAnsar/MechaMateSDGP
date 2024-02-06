package com.mechamate.entity;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Service")
public class Service {
    public enum ServiceQuality {
        EXCELLENT("Excellent"),
        GOOD("Good"),
        FAIR("Fair"),
        POOR("Poor");

        private final String description;

        ServiceQuality(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }
    @Id
    private String serviceId;

    @DBRef
    private UserProfile customerUserName;

    @DBRef
    private Maintenance appliedFor;

    private ServiceQuality qualityLevel;

    private int nextService;

    private double nextServiceInMonths;

}
