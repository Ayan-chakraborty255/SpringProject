package com.example.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "position")
public class Position {
    @Id
	private String id;
    private String product;
    private String name;
    private Integer qty;
    private double avg;
    private double price;
    private String net;
    private String day;
    
    private boolean isLoss;
    public boolean getIsLoss() {
        return isLoss;
    }
    public void setIsLoss(boolean isLoss) {
        this.isLoss = isLoss;
    }
}
