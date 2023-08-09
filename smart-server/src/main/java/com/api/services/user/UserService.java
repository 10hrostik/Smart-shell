package com.api.services.user;

import com.api.model.user.User;
import com.api.repositories.UserRepository;
import com.api.services.user.utils.RegisterUserDto;
import com.api.services.user.utils.ResponseUserDto;
import com.api.services.user.utils.UserDtoBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.getUserByUsername(username);
    }

    public ResponseUserDto registerUser(RegisterUserDto userDto) throws IllegalArgumentException{
        if (userRepository.isUser(userDto.getUsername(), userDto.getEmail())) {
            throw new IllegalArgumentException("User already exist!");
        }
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User user = UserDtoBuilder.registerDtoToUserEntity(userDto);
        userRepository.persistUser(user);

        return UserDtoBuilder.userEntityToResponseUserDto(user);
    }
}
