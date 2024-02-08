package com.mechamate.entity;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Vehicle")
public class Vehicle {

    public enum VehicleType {
        VTypeCar,
        VTypeVan,
        VTypeLorry,
        VTypeBicycle
    }

    @Id
    @NotBlank(message = "id is mandatory")
    private Long id;

    @NotBlank(message = "reg no is mandatory")
    private String regNo;

    @NotBlank(message = "vehicleType is mandatory")
    private VehicleType vehicleType;

    @DBRef
    @NotBlank(message = "owner is mandatory")
    private UserProfile owner;


}
