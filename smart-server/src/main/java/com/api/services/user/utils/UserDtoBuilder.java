package com.api.services.user.utils;

import com.api.model.user.Role;
import com.api.model.user.User;

import java.sql.Date;
import java.time.LocalDate;

public class UserDtoBuilder {
    public static User registerDtoToUserEntity(RegisterUserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());
        user.setRegisterDate(Date.valueOf(LocalDate.now()));
        user.setLoginDate(Date.valueOf(LocalDate.now()));
        user.setRole(Role.ROLE_USER);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(true);

        return user;
    }

    public static ResponseUserDto userEntityToResponseUserDto(User user) {
        ResponseUserDto userDto = new ResponseUserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setUsername(user.getUsername());
        userDto.setPhoneNumber(user.getPhoneNumber());
        userDto.setLoginDate(user.getLoginDate());
        userDto.setRole(user.getAuthorities().stream().findFirst().get().toString());

        return userDto;
    }
}
