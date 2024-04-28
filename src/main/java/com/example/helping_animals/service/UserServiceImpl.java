package com.example.helping_animals.service;

import com.example.helping_animals.dto.UserDto;
import com.example.helping_animals.dto.UserEditDto;
import com.example.helping_animals.dto.UserRegistrationDto;
import com.example.helping_animals.model.Donation;
import com.example.helping_animals.model.Income;
import com.example.helping_animals.model.Role;
import com.example.helping_animals.model.User;
import com.example.helping_animals.repository.RoleRepository;
import com.example.helping_animals.repository.UserRepository;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
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

    @Override
    public UserDto updateUser(UserEditDto userEditDto) {
        User user = userRepository.getById(userEditDto.getId());

        user.setFirstName(userEditDto.getFirstName().trim());
        user.setLastName(userEditDto.getLastName().trim());
        user.setAddress(userEditDto.getAddress().trim());
        user.setEmail(userEditDto.getEmail().trim());
        user.setInstagram(userEditDto.getInstagram().trim());
        user.setPhoneNumber(userEditDto.getPhoneNumber().trim().replaceAll("\\D", ""));

        return new UserDto(userRepository.saveAndFlush(user));
    }

    @Override
    public boolean activationUser(String email) throws Exception {
        User user = userRepository.getUserByEmail(email);
            if (user != null && !user.getActivated()){
                user.setActivated(true);
                userRepository.saveAndFlush(user);
                return true;
            }else if (user != null && user.getActivated()){
                throw new Exception("Email " + user.getEmail() + " уже активирован.");
            }else {
                throw new Exception("Некорректный токет или пользователя не существует");
            }
    }

    @Override
    public boolean deactivationUser(String email) {
        User user = userRepository.getUserByEmail(email);
        if (user != null && user.getActivated()){
            user.setActivated(false);
            userRepository.saveAndFlush(user);
            return true;
        }
        return false;
    }

}
