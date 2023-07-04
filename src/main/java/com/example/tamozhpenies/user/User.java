package com.example.tamozhpenies.user;

import com.example.tamozhpenies.clientSum.ClientSum;
import com.example.tamozhpenies.peni.Peni;
import com.example.tamozhpenies.tax.Tax;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Заполните поле названия клиента")
    @Size(min = 4)
    private String username;

    @NotEmpty(message = "Укажите пароль клиента")
    private String password;

    @Email
    private String email;

    @Pattern(regexp = "^[0-9]+$", message = "Некорректно указан регистрационный номер")
    private String registrationNumber;

    private String role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ClientSum> clientSums = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Peni> peni = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "id")
    private Tax tax;

    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public User(String username, String password, String role, String email, String registrationNumber) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.email = email;
        this.registrationNumber = registrationNumber;
    }

    public User() { }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Tax getTax() {
        return tax;
    }

    public void setTax(Tax tax) {
        this.tax = tax;
    }

    //Связать сущность пользователя и пени
    public void addPeni(Peni peni) {
        this.peni.add(peni);
        peni.setUser(this);
    }
    //Связать сущность пользователя и уплаты
    public void addClientSum(ClientSum clientSum) {
        this.clientSums.add(clientSum);
        clientSum.setUser(this);
    }
    public double getTotalPeni() {
        int sum = 0;
        if (peni.isEmpty()) {
            return sum;
        }

        for (Peni penya : peni) {
            sum += penya.getPeniAmount();
        }

        return sum;
    }

    public double getPaymentSum() {
        int sum = 0;
        if (clientSums.isEmpty()) {
            return sum;
        }

        for (ClientSum clientSum : clientSums) {
            sum += clientSum.getSum();
        }

        return sum;
    }
    public boolean hasPeni() {
        Tax tax = this.tax;
        if (tax == null)
            return false;

        LocalDate now = LocalDate.now();
        if (tax.getPayDate().isBefore(now)) {
            return true;
        }
        return false;
    }
}
