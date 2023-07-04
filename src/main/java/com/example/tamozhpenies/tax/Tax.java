package com.example.tamozhpenies.tax;

import com.example.tamozhpenies.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
public class Tax {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @PastOrPresent
    @NotNull
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate taxDate;

    @PastOrPresent
    @NotNull
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate payDate;

    @Min(value = 1)
    private double taxSum;

    @OneToOne(mappedBy = "tax", cascade = CascadeType.ALL, orphanRemoval = true)
    private User user;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setTaxDate(LocalDate taxDate) {
        this.taxDate = taxDate;
    }

    public LocalDate getTaxDate() {
        return taxDate;
    }

    public void setPayDate(LocalDate payDate) {
        this.payDate = payDate;
    }

    public LocalDate getPayDate() {
        return payDate;
    }

    public double getTaxSum() {
        return taxSum;
    }

    public void setTaxSum(double taxSum) {
        this.taxSum = taxSum;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Tax{" +
                "id=" + id +
                ", taxDate=" + taxDate +
                ", payDate=" + payDate +
                ", taxSum=" + taxSum +
                ", user=" + user +
                '}';
    }
}
