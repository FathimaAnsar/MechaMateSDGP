package com.mechamate.entity;

import jakarta.persistence.Id;

import java.util.HashMap;
import java.util.Map;

public class Language {
    @Id
    private String languageId;
    private Map<String, String> langData;
}
