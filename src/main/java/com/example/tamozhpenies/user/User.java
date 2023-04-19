package com.example.tamozhpenies.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.type.NumericBooleanConverter;


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
}
