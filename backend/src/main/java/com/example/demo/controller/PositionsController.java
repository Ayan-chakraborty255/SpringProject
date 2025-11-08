package com.example.demo.controller;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import com.example.demo.model.Position;
import com.example.demo.repository.PositionsRepository;

@RestController
@RequestMapping("/api/positions")
public class PositionsController {
    private final PositionsRepository repo;
    public PositionsController(PositionsRepository repo){
        this.repo=repo;
    }
    // @PostMapping("/insertmany")
    // public List<Position> addMultiplePositions(@RequestBody List<Position> positionsList) {
        
    //     return repo.saveAll(positionsList);
    // }
    @GetMapping("/allPositions")
    public List<Position> allPositions() {
        return repo.findAll();
    }
    
}
