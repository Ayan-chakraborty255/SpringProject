package com.example.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.config.JwtAuthFilter;
import com.example.demo.model.Holding;
import com.example.demo.repository.HoldingsRepository;
import com.example.demo.service.JwtService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RequiredArgsConstructor
@RequestMapping("/api/holdings")
@RestController


public class HoldingsController {
	private final HoldingsRepository repo;
	private final JwtService jwtService;
    // @PostMapping("/insertmany")
    // public List<Holding>addMultipleHoldings(@RequestBody List<Holding> holdingsList){
    //     return repo.saveAll(holdingsList);
    // }
    @GetMapping("/allHoldings")
    public ResponseEntity<?> getAllHoldings(@CookieValue(name = "authToken", required = false) String token){
        if(token==null){
            return ResponseEntity.status(401).body("Not LoggedIn");
        }
        String email = jwtService.extractEmail(token);
        List<Holding> data=repo.findByEmail(email);
        return ResponseEntity.ok(data);
    }
    // @GetMapping("/allHoldings")
    // public List<Holding> allHoldings() {
    //     return repo.findAll();
    // }
    
}
