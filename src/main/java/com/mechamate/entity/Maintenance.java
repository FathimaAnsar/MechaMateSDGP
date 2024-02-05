package com.mechamate.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@Document(collection = "Maintenance")

public class Maintenance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long maintenanceId;

    @NotBlank(message = "Maintenance name is mandatory")
    @Size(min = 1, max = 50, message = "Maintenance name cannot exceed 50 characters")
    private String maintenanceName;

    @NotBlank(message = "Maintenance description is mandatory")
    @Size(min = 1, max = 255, message = "Description cannot exceed 255 characters")
    private String maintenanceDescription;

}
