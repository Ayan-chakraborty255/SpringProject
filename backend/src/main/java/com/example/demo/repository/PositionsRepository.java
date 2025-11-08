package com.example.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demo.model.Position;

public interface PositionsRepository extends MongoRepository<Position,String>{

}
