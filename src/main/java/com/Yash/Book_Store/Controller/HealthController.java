package com.Yash.Book_Store.Controller;

import com.Yash.Book_Store.Repository.User_Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @Autowired
    private User_Repository userRepository; // Or any simple repository

    @GetMapping("/api/health")
    public ResponseEntity<String> healthCheck() {
        // Run a very fast, simple query just to keep the DB connection warm
        userRepository.count();
        return ResponseEntity.ok("I am alive and DB is connected");
    }
}
