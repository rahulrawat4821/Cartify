package com.rahul.cartify.dto.response;

import com.rahul.cartify.enums.Role;

public class RegisterResponse {

    private Long id;
    private String name;
    private String email;
    private String message;
    private Role role;

    public RegisterResponse() {
    }

    public RegisterResponse(
            Long id,
            String name,
            String email,
            String message,
            Role role) {

        this.id = id;
        this.name = name;
        this.email = email;
        this.message = message;
        this.role = role;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}