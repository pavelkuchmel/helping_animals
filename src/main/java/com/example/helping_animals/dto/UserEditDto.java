package com.example.helping_animals.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEditDto {

    public UserEditDto(UserDto userDto){
        this.setId(userDto.getId());
        this.setEmail(userDto.getEmail());
        this.setFirstName(userDto.getFirstName());
        this.setLastName(userDto.getLastName());
        this.setAddress(userDto.getAddress());
        this.setInstagram(userDto.getInstagram());
        this.setPhoneNumber(userDto.getPhoneNumber());
    }

    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String address;
    private String instagram;
    private String phoneNumber;

}
