package com.example.helping_animals.dto;

import com.example.helping_animals.model.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class IncomeDto {

    public IncomeDto(Income income){
        this.setId(income.getId());
        this.setUser(income.getUser());
        this.setRequiredAmount(income.getRequiredAmount());
        this.setDescription(income.getDescription());
        this.setRelevant(income.getRelevant());
        this.setAnimals(income.getAnimals());
        this.setIncomeType(income.getIncomeType());
        this.setDonations(income.getDonations());
        this.setReports(income.getReports());
        this.setCreated(new SimpleDateFormat("dd-MM-yy").format(income.getCreated().getTime()));
    }

    private Long id;
    private User user;
    private Double requiredAmount;
    private String description;
    private Boolean relevant;
    //TODO заменить стринг на нормальную дату
    private String created;
    private String updated;
    private List<Animal> animals;
    private IncomeType incomeType;
    private List<Donation> donations;
    private List<Report> reports;

}
