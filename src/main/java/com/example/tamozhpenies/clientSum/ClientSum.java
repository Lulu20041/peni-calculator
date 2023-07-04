package com.example.tamozhpenies.clientSum;

import com.example.tamozhpenies.currency.Currency;
import com.example.tamozhpenies.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PastOrPresent;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
public class ClientSum {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //Дата уплаты
    @PastOrPresent
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate date;

    //Размер уплаты
    @Min(0)
    private double sum;

    @OneToOne
    @JoinColumn(name = "currencyId", referencedColumnName = "id")
    private Currency currency;

    //Клиент
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "id")
    private User user;

    public ClientSum() { }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public Currency getCurrency() { return currency; }

    public void setCurrency(Currency currency) { this.currency = currency; }

    public User getUser() { return user; }

    public void setUser(User user) {
        this.user = user;
    }

}
