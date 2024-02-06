package com.mechamate.entity;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "TrainedModel")
public class TrainedModel {
    @Id
    private int trainedModelId;

    private String  name;

    private String description;

    private String calcFunction;

}
