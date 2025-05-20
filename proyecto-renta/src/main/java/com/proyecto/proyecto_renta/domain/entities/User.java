package com.proyecto.proyecto_renta.domain.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

@Data
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUser;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(nullable = false, unique = true, length = 90)
    private String email;

    @Column(name = "password_hash", nullable = false, length = 220)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private Role role;

    @Column(name = "registration_date", nullable = false, updatable = false)
    private LocalDateTime registrationDate = LocalDateTime.now();

    @Column(name = "account_non_expired", nullable = false)
    private boolean accountNonExpired = true;

    @Column(name = "account_non_locked", nullable = false)
    private boolean accountNonLocked = true;

    @Column(name = "credentials_non_expired", nullable = false)
    private boolean credentialsNonExpired = true;

    @Column(name = "enabled", nullable = false)
    private boolean enabled = true;

    public enum Role {
        ADMIN, PROVIDER, CLIENT
    }

    // Métodos requeridos por UserDetails

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public String getPassword() {
        return passwordHash;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    // Builder opcional para creación más limpia
    public static UserBuilder builder() {
        return new UserBuilder();
    }

    public static class UserBuilder {
        private String name;
        private String email;
        private String passwordHash;
        private Role role;

        public UserBuilder name(String name) {
            this.name = name;
            return this;
        }

        public UserBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserBuilder passwordHash(String passwordHash) {
            this.passwordHash = passwordHash;
            return this;
        }

        public UserBuilder role(Role role) {
            this.role = role;
            return this;
        }

        public User build() {
            User user = new User();
            user.setName(name);
            user.setEmail(email);
            user.setPasswordHash(passwordHash);
            user.setRole(role);
            return user;
        }
    }
}
