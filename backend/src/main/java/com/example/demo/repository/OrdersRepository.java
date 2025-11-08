package com.example.demo.repository;
import com.example.demo.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrdersRepository extends MongoRepository<Order,String>{

}
