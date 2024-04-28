package com.example.helping_animals.service;

import com.example.helping_animals.dto.AnimalDto;
import com.example.helping_animals.dto.AnimalRegistrationDto;
import com.example.helping_animals.dto.UserDto;
import com.example.helping_animals.model.Animal;
import com.example.helping_animals.model.AnimalType;
import com.example.helping_animals.repository.AnimalRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.util.List;

public interface AnimalService {

    void deleteAnimalById(Long id);
    List<AnimalDto> getAllAnimals();
    AnimalDto getAnimalById(Long id);
    List<AnimalDto> findAnimalsByAnimalType(String animalType);
    Animal saveAnimalRegistrationDto(AnimalRegistrationDto animalRegistrationDto) throws ParseException;
    List<AnimalDto> findAnimalsByUser(UserDto userDto);
}
