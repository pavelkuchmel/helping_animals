package com.example.helping_animals.model;

import com.example.helping_animals.dto.UserDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column
    private String address;

    @Column(nullable = false, unique = true)
    private String email;

    @Column
    private String instagram;

    @Column
    private String password;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column
    private String passport;

    @Column(nullable = false)
    private Boolean activated;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @Column
    @CreationTimestamp
    private Timestamp created;

    @Column
    @UpdateTimestamp
    private Timestamp updated;

    @OneToMany(mappedBy = "user")
    private List<Income> incomes;

    @OneToMany(mappedBy = "user")
    private List<Donation> donations;

}
