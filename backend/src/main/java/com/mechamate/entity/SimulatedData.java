package com.mechamate.entity;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "SimulatedData")
public class SimulatedData {
    private String simulatedKey;
    private long kmValue;
    public String getSimulatedKey() {
        return simulatedKey;
    }

    public void setSimulatedKey(String simulatedKey) {
        this.simulatedKey = simulatedKey;
    }

    public long getKmValue() {
        return kmValue;
    }

    public void setKmValue(long kmValue) {
        this.kmValue = kmValue;
    }


}
