package com.mechamate.entity;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.awt.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Theme")
public class Theme {
    @Id
    private int themeId;

    private String name;

    private String description;

    private Color[] colorList;

    private Font[] fontList;


}
