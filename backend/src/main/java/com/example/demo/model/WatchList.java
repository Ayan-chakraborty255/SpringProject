package com.example.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collation = "watchlist")
public class WatchList {
    @Id
    private String id;
    private String name;
    private double price;
    private String percent;
    private boolean isDown;
}
// name: "WIPRO",
//     price: 577.75,
//     percent: "0.32%",
//     isDown: false,