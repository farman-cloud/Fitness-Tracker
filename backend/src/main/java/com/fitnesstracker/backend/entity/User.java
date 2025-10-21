package com.fitnesstracker.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data
@Entity
@Table(name="users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "UserName is a required field.")
    private String username;

    @NotBlank(message = "Email is a required field.")
    @Column(unique = true)
    private String email;

    private String password;

    @Column(nullable = true)
    private Integer age;

    @Column(nullable = true)
    private Double weight;

    @Column(nullable = true)
    private Double height;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // To-do: Add roles.
        return null;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
