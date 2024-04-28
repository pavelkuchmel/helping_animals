package com.example.helping_animals.service;

import com.example.helping_animals.model.AnimalType;

import java.util.List;

public interface AnimalTypeService {
    AnimalType findAnimalTypeByName(String name);

    List<AnimalType> findAllAnimalTypes();
}
