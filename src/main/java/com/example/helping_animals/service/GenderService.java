package com.example.helping_animals.service;

import com.example.helping_animals.model.Gender;

public interface GenderService {
    Gender findGenderByName(String name);
}
