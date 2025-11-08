package com.example.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "order")
public class Order {
    @Id
    private String id;
    private String name;
    private Integer qty;
    private double price;
    private String mode;
}
