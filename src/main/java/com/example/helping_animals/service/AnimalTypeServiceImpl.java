package com.example.helping_animals.service;

import com.example.helping_animals.model.AnimalType;
import com.example.helping_animals.repository.AnimalTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnimalTypeServiceImpl implements AnimalTypeService{

    @Autowired
    private AnimalTypeRepository animalTypeRepository;

    @Override
    public AnimalType findAnimalTypeByName(String name) {
        return animalTypeRepository.findAnimalTypeByName(name);
    }

    @Override
    public List<AnimalType> findAllAnimalTypes() {
        return animalTypeRepository.findAll();
    }


}
