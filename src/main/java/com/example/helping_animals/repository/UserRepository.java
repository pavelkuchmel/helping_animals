package com.example.helping_animals.repository;

import com.example.helping_animals.dto.UserDto;
import com.example.helping_animals.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User getUserByEmail(String email);
}
