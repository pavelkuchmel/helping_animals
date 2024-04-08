package com.example.helping_animals.repository;

import com.example.helping_animals.model.Animal;
import com.example.helping_animals.model.AnimalType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnimalRepository extends JpaRepository<Animal, Long> {
    List<Animal> findAnimalsByAnimalType(AnimalType animalType);
}
