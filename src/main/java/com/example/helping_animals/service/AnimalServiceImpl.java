package com.example.helping_animals.service;

import com.example.helping_animals.dto.AnimalDto;
import com.example.helping_animals.model.Animal;

import com.example.helping_animals.model.AnimalType;
import com.example.helping_animals.repository.AnimalRepository;
import com.example.helping_animals.repository.AnimalTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnimalServiceImpl implements AnimalService{

    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private AnimalTypeRepository animalTypeRepository;

    @Override
    public List<AnimalDto> getAllAnimals() {
        return animalRepository.findAll().stream().map(AnimalDto::new).collect(Collectors.toList());
    }

    @Override
    public AnimalDto getAnimalById(Long id) {
        return new AnimalDto(animalRepository.getReferenceById(id));
        //return animalToAnimalDto(animalRepository.getReferenceById(id));
    }

    @Override
    public List<AnimalDto> findAnimalsByAnimalType(String string) {
        AnimalType animalType = animalTypeRepository.findAnimalTypeByName(string.trim().toUpperCase());
        return animalRepository.findAnimalsByAnimalType(animalType).stream().map(AnimalDto::new).collect(Collectors.toList());
    }

    private AnimalDto animalToAnimalDto(Animal animal){
        AnimalDto animalDto = new AnimalDto();
        animalDto.setId(animal.getId());
        animalDto.setUser(animal.getUser());
        animalDto.setName(animal.getName());
        animalDto.setAge(animal.getAge());
        animalDto.setGender(animal.getGender());
        animalDto.setHeight(animal.getHeight());
        animalDto.setDescription(animal.getDescription());
        animalDto.setPhoto(animal.getPhoto());
        animalDto.setAnimalType(animal.getAnimalType());
        animalDto.setCreated(animal.getCreated());
        animalDto.setCreated(animal.getCreated());
        animalDto.setIncome(animal.getIncome());
        return animalDto;
    }
}
