package com.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @GetMapping("api/auth/test")
    public ResponseEntity<?> testEndpoint() {
        return ResponseEntity.ok(ResponseEntity.status(200));
    }
}
