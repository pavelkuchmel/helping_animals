package com.example.helping_animals.repository;

import com.example.helping_animals.model.Income;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IncomeRepository extends JpaRepository<Income, Long> {
}
