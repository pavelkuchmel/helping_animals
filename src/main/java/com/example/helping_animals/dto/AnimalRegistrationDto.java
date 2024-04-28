package com.example.helping_animals.dto;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AnimalRegistrationDto {
    private String userEmail;
    private String name;
    private String animalType;
    private String gender;
    private Integer age;
    private Integer height;
    private String description;
    private Boolean sterilization;
    private String vaccinated;
    private Boolean chipped;
    private Boolean toiletOutside;
    private String photoUrl;
}
