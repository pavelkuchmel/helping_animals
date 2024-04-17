package com.example.helping_animals.service;

import com.example.helping_animals.dto.UserDto;
import com.example.helping_animals.dto.UserRegistrationDto;
import com.example.helping_animals.model.Role;
import com.example.helping_animals.model.User;
import com.example.helping_animals.repository.RoleRepository;
import com.example.helping_animals.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream().map(UserDto::new).collect(Collectors.toList());
    }

    @Override
    public UserDto getUserById(Long id) {
        return new UserDto(userRepository.getReferenceById(id));
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

    @Override
    public UserDto findUserDtoByEmail(String email) {
        return  new UserDto(userRepository.getUserByEmail(email));
    }

    @Override
    public User saveUser(UserRegistrationDto userRegistrationDto) {
        User user = new User();
        user.setFirstName(userRegistrationDto.getFirstName());
        user.setLastName(userRegistrationDto.getLastName());
        user.setEmail(userRegistrationDto.getEmail());

        user.setPassword(passwordEncoder.encode(userRegistrationDto.getPassword()));
        Role role = roleRepository.findRoleByName("ROLE_GENERAL");
        user.setRole(role);
        user.setActivated(false);
        return userRepository.save(user);
    }

}
