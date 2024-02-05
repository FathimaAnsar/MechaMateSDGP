package com.mechamate.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data
@Document(collection = "UserProfile")
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private ObjectId id;

    @NotBlank(message = "name is mandatory")
    private String name;

//    @NotBlank(message = "password is mandatory")
//    @Size(min = 1, max = 8, message = "password length must be between 1 - 8")
//    private String password;

    @DBRef
    private List<Vehicle> vehicles;



}
