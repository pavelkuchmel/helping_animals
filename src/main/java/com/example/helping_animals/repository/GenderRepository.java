package com.example.helping_animals.repository;

import com.example.helping_animals.model.Animal;
import com.example.helping_animals.model.Gender;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenderRepository extends JpaRepository<Gender, Long> {
    Gender findGenderByName(String name);
}
