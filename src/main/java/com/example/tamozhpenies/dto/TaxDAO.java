package com.example.tamozhpenies.dto;

import com.example.tamozhpenies.validation.DateOrder;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@DateOrder
public class TaxDAO {

    @PastOrPresent
    @NotNull
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "dd.MM.yyyy")
    private LocalDate taxDate;

    @PastOrPresent
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "dd.MM.yyyy")
    private LocalDate payDate;

    @DecimalMin(value = "0.01", message = "Сумма должны быть больше нуля")
    private double taxSum;

    public LocalDate getTaxDate() {
        return taxDate;
    }

    public void setTaxDate(LocalDate taxDate) {
        this.taxDate = taxDate;
    }

    public double getTaxSum() {
        return taxSum;
    }

    public void setTaxSum(double taxSum) {
        this.taxSum = taxSum;
    }

    public LocalDate getPayDate() {
        return payDate;
    }

    public void setPayDate(LocalDate payDate) {
        this.payDate = payDate;
    }
}
