package com.kane.model;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class Category {
    private Long id;
    private String name;
    private String type;
    private String color;
    private String icon;

    public Category() {
    }

    public Category(String icon, String color, String type, String name, Long id) {
        this.icon = icon;
        this.color = color;
        this.type = type;
        this.name = name;
        this.id = id;
    }
}
