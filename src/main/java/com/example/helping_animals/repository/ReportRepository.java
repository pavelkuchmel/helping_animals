package com.example.helping_animals.repository;

import com.example.helping_animals.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {
}
