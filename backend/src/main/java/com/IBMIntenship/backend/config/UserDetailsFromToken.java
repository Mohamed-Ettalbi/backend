package com.IBMIntenship.backend.config;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.util.List;

public class UserDetailsFromToken {
    private String email;
    private List<String> roles;

    public UserDetailsFromToken(String email, List<String> roles) {
        this.email = email;
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    public List<String> getRoles() {
        return roles;
    }

    @Override
    public String toString() {
        return "UserDetailsFromToken{" +
                "email='" + email + '\'' +
                ", roles=" + roles +
                '}';
    }



}

