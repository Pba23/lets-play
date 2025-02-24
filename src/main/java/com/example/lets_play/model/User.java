package com.example.lets_play.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Data
@Document(collection = "users")
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    private String id;
    @NotBlank(message = "Le nom d'utilisateur est obligatoire.")
    private String name;

    @NotBlank(message = "L'email est obligatoire.")
    private String email;

    @NotBlank(message = "Le rôle est obligatoire.")
    @Pattern(regexp = "^(admin|user)$", message = "Le rôle doit être soit 'admin' soit 'user'.")
    private String role;
    private String password;
    public static Object withUsername(String string) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'withUsername'");
    }
      public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role)); // Convertir le rôle en GrantedAuthority
    }
}
