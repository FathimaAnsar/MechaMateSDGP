package com.mechamate.entity;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Data
@AllArgsConstructor
@Document(collection = "Feature")
public class Feature {
    @Id
    private ObjectId featureId;

    @NotBlank(message = "Feature name cannot be empty")
    @Size(min = 1, max = 50, message = "Feature name cannot exceed 50 characters")
    private String featureName;

    @NotBlank(message = "Maintenance description is mandatory")
    @Size(min = 1, max = 255, message = "Description cannot exceed 255 characters")
    private String featureDescription;

}
