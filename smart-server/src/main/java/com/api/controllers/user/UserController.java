package com.api.controllers.user;

import com.api.services.user.UserService;
import com.api.services.user.utils.EditPasswordDto;
import com.api.services.user.utils.EditUserDto;
import com.api.services.user.utils.ResponseUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/secured/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PutMapping(value = "/edit", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseUserDto> edit(@RequestBody EditUserDto editUserDto) {
        ResponseUserDto response = userService.edit(editUserDto);

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON).body(response);
    }

    @PatchMapping(value = "/edit/password", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void editPassword(@RequestBody EditPasswordDto editPasswordDto) {
        userService.editPassword(editPasswordDto);
    }

    @DeleteMapping(value = "/delete")
    public void delete(@RequestBody Long id) {
        userService.delete(id);
    }
}
