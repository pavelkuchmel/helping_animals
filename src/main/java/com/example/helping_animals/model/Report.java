package com.example.helping_animals.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "reports")
public class Report implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "income_id")
    private Income income;

    @Column
    private String name;

    @Column
    private String img;

    @Column
    private Timestamp date;

    @Column
    private Double price;

    @Column
    private String additional;

}
