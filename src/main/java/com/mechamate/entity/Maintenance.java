package com.mechamate.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Data
@Document(collection = "maintenances")
public class Maintenance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is mandatory")
    @Size(min = 1, max = 100, message = "Name length must be between 1 - 100")
    private String name;

    @NotBlank(message = "Description is mandatory")
    @Size(min = 1, max = 255, message = "Description length must be between 1 - 255")
    private String description;

}
