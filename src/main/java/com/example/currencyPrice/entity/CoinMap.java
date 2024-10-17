package com.example.currencyPrice.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "coin")
public class CoinMap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="chinese")
    private String coinChinese;

    @Column(name="english")
    private String coinEnglish;

}
