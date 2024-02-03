package com.example.sbb.entity;


import jakarta.persistence.*;

@Entity
public class Sbb {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;
}
