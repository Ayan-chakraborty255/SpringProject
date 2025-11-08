package com.example.demo.repository;
import com.example.demo.model.WatchList;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WatchListRepository extends MongoRepository<WatchList,String>{

}
