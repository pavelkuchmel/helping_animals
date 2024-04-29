package com.example.helping_animals.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "income")
public class Income implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "required_amount")
    private Double requiredAmount;

    @Column
    private String description;

    @Column
    private Boolean relevant;

    @Column
    @CreationTimestamp
    private Timestamp created;

    @Column
    @UpdateTimestamp
    private Timestamp updated;

    @OneToMany(mappedBy = "income", fetch = FetchType.EAGER)
    private List<Animal> animals;

    @ManyToOne
    @JoinColumn(name = "income_type_id")
    private IncomeType incomeType;

    @OneToMany(mappedBy = "income")
    private List<Donation> donations;

    @OneToMany(mappedBy = "income")
    private List<Report> reports;
}
