package com.mechamate.entity;

import jakarta.persistence.Id;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class Language {
    @Id
    private String languageId;
    private Map<String, String> langData;
}
