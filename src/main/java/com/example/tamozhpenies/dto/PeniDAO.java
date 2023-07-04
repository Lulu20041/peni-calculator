package com.example.tamozhpenies.dto;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

//DAO таможенной пени для взаимодействия с формой
public class PeniDAO {

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "dd.MM.yyyy")
    @PastOrPresent
    private LocalDate peniDate;

    public LocalDate getPeniDate() {
        return peniDate;
    }

    public PeniDAO setPeniDate(LocalDate peniDate) {
        this.peniDate = peniDate;
        return this;
    }
}
