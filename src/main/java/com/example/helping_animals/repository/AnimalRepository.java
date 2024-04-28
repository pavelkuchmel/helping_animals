package com.example.helping_animals.repository;

import com.example.helping_animals.model.Animal;
import com.example.helping_animals.model.AnimalType;
import com.example.helping_animals.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AnimalRepository extends JpaRepository<Animal, Long> {
    List<Animal> findAnimalsByAnimalType(AnimalType animalType);
    @Query(
            value = "SELECT * FROM animals a WHERE a.user_id = ?1",
            nativeQuery = true)
    List<Animal> findAnimalsByUserId(Long id);
}
