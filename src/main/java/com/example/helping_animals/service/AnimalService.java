package com.example.helping_animals.service;

import com.example.helping_animals.dto.AnimalDto;
import com.example.helping_animals.model.AnimalType;
import com.example.helping_animals.repository.AnimalRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface AnimalService {

    List<AnimalDto> getAllAnimals();
    AnimalDto getAnimalById(Long id);

    List<AnimalDto> findAnimalsByAnimalType(String animalType);

}
