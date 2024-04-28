package com.example.helping_animals.service;

import com.example.helping_animals.model.Gender;
import com.example.helping_animals.repository.GenderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GenderServiceImpl implements GenderService{

    @Autowired
    private GenderRepository genderRepository;

    @Override
    public Gender findGenderByName(String name) {
        return genderRepository.findGenderByName(name);
    }
}
