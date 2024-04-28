package com.example.helping_animals.service;

import com.example.helping_animals.dto.AnimalDto;
import com.example.helping_animals.dto.AnimalRegistrationDto;
import com.example.helping_animals.dto.UserDto;
import com.example.helping_animals.model.Animal;

import com.example.helping_animals.model.AnimalType;
import com.example.helping_animals.repository.AnimalRepository;
import com.example.helping_animals.repository.AnimalTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnimalServiceImpl implements AnimalService{

    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private AnimalTypeService animalTypeService;

    @Autowired
    private UserService userService;

    @Autowired
    private GenderService genderService;

    @Override
    public void deleteAnimalById(Long id) {

        animalRepository.deleteById(id);
    }

    @Override
    public List<AnimalDto> getAllAnimals() {
        return animalRepository.findAll().stream().map(AnimalDto::new).collect(Collectors.toList());
    }

    @Override
    public AnimalDto getAnimalById(Long id) {
        return new AnimalDto(animalRepository.getReferenceById(id));
    }

    @Override
    public List<AnimalDto> findAnimalsByAnimalType(String string) {
        AnimalType animalType = animalTypeService.findAnimalTypeByName(string.trim().toUpperCase());
        return animalRepository.findAnimalsByAnimalType(animalType).stream().map(AnimalDto::new).collect(Collectors.toList());
    }

    @Override
    public Animal saveAnimalRegistrationDto(AnimalRegistrationDto animalRegistrationDto) throws ParseException {

        return animalRepository.saveAndFlush(animalMapRegistrationDtoToAnimal(animalRegistrationDto));
    }

    @Override
    public List<AnimalDto> findAnimalsByUser(UserDto userDto) {
        return animalRepository.findAnimalsByUserId(userDto.getId()).stream().map(AnimalDto::new).collect(Collectors.toList());
    }

    private AnimalDto animalMapToAnimalDto(Animal animal){
        AnimalDto animalDto = new AnimalDto();
        animalDto.setId(animal.getId());
        animalDto.setUser(animal.getUser());
        animalDto.setName(animal.getName().trim());
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

    private Animal animalMapRegistrationDtoToAnimal(AnimalRegistrationDto animalRegistrationDto) throws ParseException {
        Animal animal = new Animal();

        animal.setUser(userService.findUserByEmail(animalRegistrationDto.getUserEmail()));
        animal.setName(animalRegistrationDto.getName());
        animal.setAnimalType(animalTypeService.findAnimalTypeByName(animalRegistrationDto.getAnimalType()));
        animal.setGender(genderService.findGenderByName(animalRegistrationDto.getGender()));
        animal.setAge(animalRegistrationDto.getAge());
        animal.setHeight(animalRegistrationDto.getHeight());
        animal.setDescription(animalRegistrationDto.getDescription());
        animal.setSterilization(animalRegistrationDto.getSterilization());
        animal.setVaccinated(animalRegistrationDto.getVaccinated().isBlank() ? null : new Timestamp(new SimpleDateFormat("yyyy-MM-dd").parse(animalRegistrationDto.getVaccinated()).getTime()));
        animal.setChipped(animalRegistrationDto.getChipped());
        animal.setToiletOutside(animalRegistrationDto.getToiletOutside());
        animal.setPhoto(animalRegistrationDto.getPhotoUrl());

        return animal;
    }
}
