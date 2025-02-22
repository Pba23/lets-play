package com.example.lets_play.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private String id;
    @NotBlank(message = "Le nom d'utilisateur est obligatoire.")
    private String name;

    @NotBlank(message = "L'email est obligatoire.")
    private String email;

    @Pattern(regexp = "^(admin|user)$", message = "Le rôle doit être soit 'admin' soit 'user'.")
    private String role;
}
