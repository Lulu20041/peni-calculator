package com.example.tamozhpenies.dto;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PastOrPresent;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

//DAO уплаты клиента для взаимодействия с формой
public class ClientSumDAO {

    @PastOrPresent(message = "Укажите текущую или прошлую дату")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate date;

    @DecimalMin(value = "0.01", message = "Сумма должна быть больше нуля")
    private double sum;

    private Long currencyId;

    public LocalDate getDate() {
        return date;
    }

    public double getSum() {
        return sum;
    }

    public Long getCurrencyId() {
        return currencyId;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public void setCurrencyId(Long currencyId) {
        this.currencyId = currencyId;
    }
}



