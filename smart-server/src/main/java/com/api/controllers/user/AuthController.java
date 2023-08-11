package com.api.controllers.user;

import com.api.services.user.AuthService;
import com.api.services.user.utils.RegisterUserDto;
import com.api.services.user.utils.ResponseUserDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/public/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseUserDto> register(@RequestBody RegisterUserDto userDto,
                                                     HttpServletRequest request) throws ServletException {
        ResponseUserDto response = authService.registerUser(userDto);
        request.login(userDto.getUsername(), userDto.getPassword());

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON).body(response);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<ResponseUserDto> login(@RequestParam String credential,
                                                 @RequestParam String password,
                                                  HttpServletRequest request) throws ServletException {
        ResponseUserDto response = authService.getUser(credential, password);
        request.login(credential, password);

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON).body(response);
    }
}
