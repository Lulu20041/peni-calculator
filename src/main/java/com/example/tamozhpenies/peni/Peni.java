package com.example.tamozhpenies.peni;

import com.example.tamozhpenies.refinancingRate.RefinancingRate;
import com.example.tamozhpenies.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

@Entity
public class Peni {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @PastOrPresent
    @NotNull
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate periodBegin;
    @PastOrPresent
    @NotNull
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate periodEnd;
    @Min(0)
    private int amountOfDays;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "refinancingRate_id", referencedColumnName = "id")
    private RefinancingRate refinancingRate;
    @Min(0)
    private double taxSum;
    @Min(0)
    private double peniAmount;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "id")
    private User user;
    public Peni() { }

    public LocalDate getPeriodBegin() {
        return periodBegin;
    }

    public void setPeriodBegin(LocalDate periodBegin) {
        this.periodBegin = periodBegin;
    }

    public LocalDate getPeriodEnd() {
        return periodEnd;
    }

    public void setPeriodEnd(LocalDate periodEnd) {
        this.periodEnd = periodEnd;
    }

    public double getPeniAmount() {
        return peniAmount;
    }

    public void setPeniAmount(double peniAmount) {
        this.peniAmount = peniAmount;
    }

    public double getTaxSum() {
        return taxSum;
    }

    public void setTaxSum(double taxSum) {
        this.taxSum = taxSum;
    }

    public int getAmountOfDays() {
        return amountOfDays;
    }

    public void setAmountOfDays(int amountOfDays) {
        this.amountOfDays = amountOfDays;
    }

    public RefinancingRate getRefinancingRate() {
        return refinancingRate;
    }
    public void setRefinancingRate(RefinancingRate refinancingRate) {
        this.refinancingRate = refinancingRate;
    }
}
