package com.rahul.cartify.dto.request;

import com.rahul.cartify.enums.Role;

public class RegisterRequest {

    private String name;
    private String email;
    private String password;
    private Role role;

    public RegisterRequest() {}

    
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}