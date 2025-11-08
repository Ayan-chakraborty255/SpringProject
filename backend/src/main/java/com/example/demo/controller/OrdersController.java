package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Order;
import com.example.demo.repository.OrdersRepository;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@RequestMapping("/api/orders")
@RestController
public class OrdersController {
    private final OrdersRepository repo;

    @PostMapping("/newOrder")
    public Order addToDataBase(@RequestBody Order newOrder) {
        return repo.save(newOrder);
    }
    @GetMapping("/allOrders")
    public List<Order> getAllOrders() {
        return repo.findAll();
    }
}
