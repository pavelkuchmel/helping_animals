package com.example.helping_animals.service;

import com.example.helping_animals.dto.UserDto;
import com.example.helping_animals.dto.UserRegistrationDto;
import com.example.helping_animals.model.User;

import java.util.List;

public interface UserService {

    List<UserDto> getAllUsers();
    UserDto getUserById(Long id);
    User findUserByEmail(String email);
    UserDto findUserDtoByEmail(String email);

    User saveUser(UserRegistrationDto userRegistrationDto);
}
