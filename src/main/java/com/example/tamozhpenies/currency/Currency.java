package com.example.tamozhpenies.currency;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;

@Entity
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    //Отношение валюты к BYN
    @Min(0)
    private double officialRate;

    public Currency() { }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getOfficialRate() {
        return officialRate;
    }

    public void setOfficialRate(double officialRate) {
        this.officialRate = officialRate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
