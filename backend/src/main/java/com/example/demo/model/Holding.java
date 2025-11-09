package com.example.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "holding")
public class Holding {
	@Id
	private String id;
	private String name;
    private Integer qty;
    private Double avg;
    private Double price;
    private String net;
    private String day;
    private String email;
	
	
}
