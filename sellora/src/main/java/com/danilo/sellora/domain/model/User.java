package com.danilo.sellora.domain.model;

import com.danilo.sellora.domain.model.enums.UserType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "users")

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "Name is required")
    private String name;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "Document is required")
    private String document;

    @Email
    @Column(nullable = false, unique = true)
    @NotBlank(message = "Email is required")
    private String email;

    @Column(nullable = false)
    @NotBlank(message = "Password is required")
    private String password;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "User type is required")
    private UserType userType;

    @Column(nullable = false)
    private Boolean walletEligible = false;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders;

    public User() {
    }

    public User(String name, String document, String email, String password, UserType userType) {
        this.name = name;
        this.document = document;
        this.email = email;
        this.password = password;
        this.userType = userType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank(message = "Name is required") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "Name is required") String name) {
        this.name = name;
    }

    public @NotBlank(message = "Document is required") String getDocument() {
        return document;
    }

    public void setDocument(@NotBlank(message = "Document is required") String document) {
        this.document = document;
    }

    public @Email @NotBlank(message = "Email is required") String getEmail() {
        return email;
    }

    public void setEmail(@Email @NotBlank(message = "Email is required") String email) {
        this.email = email;
    }

    public @NotBlank(message = "Password is required") String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank(message = "Password is required") String password) {
        this.password = password;
    }

    public @NotNull(message = "User type is required") UserType getUserType() {
        return userType;
    }

    public void setUserType(@NotNull(message = "User type is required") UserType userType) {
        this.userType = userType;
    }

    public Boolean getWalletEligible() {
        return walletEligible;
    }

    public void setWalletEligible(Boolean walletEligible) {
        this.walletEligible = walletEligible;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}