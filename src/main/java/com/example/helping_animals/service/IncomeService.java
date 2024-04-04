package com.example.helping_animals.service;

import com.example.helping_animals.dto.IncomeDto;

import java.util.List;

public interface IncomeService {
    List<IncomeDto> getAllIncomes();
    IncomeDto getIncomeById(Long id);
}
