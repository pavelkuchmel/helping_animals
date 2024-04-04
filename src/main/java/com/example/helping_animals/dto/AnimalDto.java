package com.example.helping_animals.dto;

import com.example.helping_animals.model.*;
import com.example.helping_animals.util.CustomTimestamp;
import lombok.*;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AnimalDto {

    public AnimalDto(Animal animal){
        this.setId(animal.getId());
        this.setUser(animal.getUser());
        this.setName(animal.getName());
        this.setAge(animal.getAge());
        this.setGender(animal.getGender());
        this.setHeight(animal.getHeight());
        this.setDescription(animal.getDescription());
        this.setPhoto(animal.getPhoto());
        this.setAnimalType(animal.getAnimalType());
        this.setCreated(animal.getCreated());
        this.setUpdated(animal.getUpdated());
        this.setIncome(animal.getIncome());
        this.setSterilization(animal.getSterilization());
        this.setVaccinated(new CustomTimestamp(animal.getVaccinated()));
//        try {
//            this.setVaccinated(new SimpleDateFormat("dd/MM/yyyy").parse(animal.getVaccinated().getDate() + "/" + (animal.getVaccinated().getMonth() + 1) + "/" + (animal.getVaccinated().getYear() + 1900)));
//        } catch (ParseException e) {
//            this.setVaccinated(null);
//        }
//        System.out.println(animal.getVaccinated().getDate() + " . " + (animal.getVaccinated().getMonth() + 1) + " . " + (animal.getVaccinated().getYear() + 1900));
        this.setToiletOutside(animal.getToiletOutside());
        this.setGenderTitle(animal.getGender().getName().equals("MALE") ? "Мальчик" : "Девочка");
        switch (getAnimalType().getName()){
            case "DOG":
                this.setGenderSample(animal.getAge() <= 12 ? " этому щенку" :" этой собачке");
                break;
            case "CAT":
                if (animal.getAge() <= 12){
                    this.setGenderSample(" этому котенку");
                }
                else {
                    switch (animal.getGender().getName()){
                        case "MALE":
                            this.setGenderSample(" этому коту");
                            break;
                        case "FEMALE":
                            this.setGenderSample(" этой кошке");
                            break;
                    }
                }
        }
    }

    private long id;
    private User user;
    private String name;
    private Integer age;
    private Gender gender;
    private String genderTitle;
    private String genderSample;
    private Integer height;
    private String description;
    private String photo;
    private Boolean sterilization;
    private CustomTimestamp vaccinated;
    private Boolean toiletOutside;
    //private Owner owner;
    private Income income;
    private AnimalType animalType;
    private Timestamp created;
    private Timestamp updated;
}
