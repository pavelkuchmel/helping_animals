package com.example.helping_animals.service;

import com.example.helping_animals.dto.UserDto;

import java.util.List;

public interface UserService {

    List<UserDto> getAllUsers();
    UserDto getUserById(Long id);

}
