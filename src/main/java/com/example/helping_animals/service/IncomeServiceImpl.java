package com.example.helping_animals.service;

import com.example.helping_animals.dto.IncomeDto;
import com.example.helping_animals.repository.IncomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IncomeServiceImpl implements IncomeService{

    @Autowired
    private IncomeRepository incomeRepository;

    @Override
    public List<IncomeDto> getAllIncomes() {
        return incomeRepository.findAll().stream().map(IncomeDto::new).collect(Collectors.toList());
    }

    @Override
    public IncomeDto getIncomeById(Long id) {
        return new IncomeDto(incomeRepository.getReferenceById(id));
    }
}
