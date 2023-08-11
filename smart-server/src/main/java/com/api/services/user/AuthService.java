package com.api.services.user;

import com.api.model.user.User;
import com.api.services.user.utils.RegisterUserDto;
import com.api.services.user.utils.ResponseUserDto;
import com.api.services.user.utils.UserDtoBuilder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;

@Service
public class AuthService extends GeneralUserService {
    public ResponseUserDto registerUser(RegisterUserDto userDto) throws IllegalArgumentException{
        if (userRepository.isUser(userDto.getUsername(), userDto.getEmail())) {
            throw new IllegalArgumentException("User already exist!");
        }
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User user = UserDtoBuilder.registerDtoToUserEntity(userDto);
        userRepository.persistUser(user);

        return UserDtoBuilder.userEntityToResponseUserDto(user);
    }

    public ResponseUserDto getUser(String credential, String password) {
        User user = (User) loadUserByUsername(credential);
        password = passwordEncoder.encode(password);
        if (!user.getPassword().equals(password)) throw new IllegalArgumentException("Wrong password");
        user.setLoginDate(Date.valueOf(LocalDate.now()));
        userRepository.update(user);

        return UserDtoBuilder.userEntityToResponseUserDto(user);
    }
}
