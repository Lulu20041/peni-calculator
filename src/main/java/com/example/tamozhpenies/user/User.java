package com.example.tamozhpenies.user;

import com.example.tamozhpenies.clientSum.ClientSum;
import com.example.tamozhpenies.peni.Peni;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.type.NumericBooleanConverter;

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
    @Convert(converter = NumericBooleanConverter.class)
    @ColumnDefault("0")
    private boolean isAdmin;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ClientSum> clientSums = new ArrayList<>();
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Peni> peni = new ArrayList<>();
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User() { }
    public Long getId() {
        return id;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public boolean isAdmin() {
        return isAdmin;
    }
    public void setName(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void addPeni(Peni peni) {
        this.peni.add(peni);
        peni.setUser(this);
    }
}
