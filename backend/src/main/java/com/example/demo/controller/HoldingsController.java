package com.example.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Holding;
import com.example.demo.repository.HoldingsRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RequestMapping("/api/holdings")
@RestController


public class HoldingsController {
	private final HoldingsRepository repo;
	public HoldingsController(HoldingsRepository repo) {
		this.repo = repo;
    }
    // @PostMapping("/insertmany")
    // public List<Holding>addMultipleHoldings(@RequestBody List<Holding> holdingsList){
    //     return repo.saveAll(holdingsList);
    // }
    @GetMapping("/allHoldings")
    public List<Holding> allHoldings() {
        return repo.findAll();
    }
    
}
