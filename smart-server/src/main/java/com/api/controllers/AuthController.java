package com.api.controllers;

import com.api.services.user.UserService;
import com.api.services.user.utils.RegisterUserDto;
import com.api.services.user.utils.ResponseUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @Autowired
    private UserService userService;

    @PostMapping(value = "api/public/auth/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseUserDto> register(@RequestBody RegisterUserDto userDto) {
        ResponseUserDto response = userService.registerUser(userDto);

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON).body(response);
    }
}
