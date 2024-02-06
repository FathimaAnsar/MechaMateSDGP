package com.mechamate.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "ServiceRecord")
public class ServiceRecord {
    public enum ServiceRecordType {
        OIL_CHANGE,
        TIRE_ROTATION,
        BRAKE_INSPECTION,
        ENGINE_TUNING,
        WHEEL_ALIGNMENT,
        FLUID_CHECK,
        AIR_FILTER_REPLACEMENT,
        BATTERY_CHECK,
        OTHER
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String serviceRecordId;

    private ServiceRecordType serviceType;

    private Date dateCompleted;

    @DBRef
    private List<Service> serviceList;

}

