package com.simeon.lab4.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigInteger;

@Entity
@Table(name = "basic_auth_user")
public class User {
    @Id
    private BigInteger id;

    @Column(name = "password")
    private String password;

    @Column(name = "username", unique = true)
    private String username;

    public BigInteger getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}