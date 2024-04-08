package com.example.helping_animals.dto;

import com.example.helping_animals.model.Donation;
import com.example.helping_animals.model.Income;
import com.example.helping_animals.model.Role;
import com.example.helping_animals.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    public UserDto(User user){
        this.setId(user.getId());
        this.setFirstName(user.getFirstName());
        this.setLastName(user.getLastName());
        this.setAddress(user.getAddress());
        this.setEmail(user.getEmail());
        this.setPhoneNumber(user.getPhoneNumber());
        this.setRole(user.getRole());
        this.setIncomes(user.getIncomes());
        this.setDonations(user.getDonations());
        this.setActivated(user.getActivated());
        this.setCreated(user.getCreated());
        this.setDateCrated(user.getCreated() != null ? new SimpleDateFormat("dd-MM-yy").format(user.getCreated().getTime()) : "");
        this.setUpdated(user.getUpdated());
        this.setDateUpdated(user.getUpdated() != null ? new SimpleDateFormat("dd-MM-yy").format(user.getUpdated().getTime()) : "");
    }


    private long id;
    private String firstName;
    private String lastName;
    private String address;
    private String email;
    //private String password;
    private String phoneNumber;
    private Boolean activated;
    private Role role;
    private Timestamp created;
    private String dateCrated;
    private Timestamp updated;
    private String dateUpdated;
    private List<Income> incomes;
    private List<Donation> donations;
}
