package com.kane.model;

import lombok.Getter;
import lombok.Setter;
import java.sql.Timestamp;

@Getter
@Setter
public class Operation {
    private Long id;
    private Long userId;
    private int categoryId;
    private double amount;
    private String type;
    private Timestamp created_at;
    private String note;
    private String categoryName; // для отображения имени категории

    public Operation() {}

    public Operation(Long id, Long userId, int categoryId, double amount, String type, Timestamp created_at, String note, String categoryName) {
        this.id = id;
        this.userId = userId;
        this.categoryId = categoryId;
        this.amount = amount;
        this.type = type;
        this.created_at = created_at;
        this.note = note;
        this.categoryName = categoryName;
    }
}
