package com.example.helping_animals.model;

import com.example.helping_animals.dto.AnimalRegistrationDto;
import com.example.helping_animals.service.AnimalTypeService;
import com.example.helping_animals.service.UserService;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "animals")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Animal implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private String name;

    @Column
    private Integer age;

    @ManyToOne
    @JoinColumn(name = "gender_id")
    private Gender gender;

    @Column
    private Integer height;

    @Column(columnDefinition = "varchar(1020)")
    private String description;

    @Column
    private Boolean sterilization;

    @Column
    private Timestamp vaccinated;

    @Column
    private Boolean chipped;

    @Column(name = "toilet_outside")
    private Boolean toiletOutside;

    @Column
    private String photo;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @ManyToOne
    @JoinColumn(name = "income_id")
    private Income income;

    @ManyToOne
    @JoinColumn(name = "animal_type_id")
    private AnimalType animalType;

    @Column
    @CreationTimestamp
    private Timestamp created;

    @Column
    @UpdateTimestamp
    private Timestamp updated;
}