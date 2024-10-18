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


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCoinChinese() {
        return coinChinese;
    }

    public void setCoinChinese(String coinChinese) {
        this.coinChinese = coinChinese;
    }

    public String getCoinEnglish() {
        return coinEnglish;
    }

    public void setCoinEnglish(String coinEnglish) {
        this.coinEnglish = coinEnglish;
    }
}
