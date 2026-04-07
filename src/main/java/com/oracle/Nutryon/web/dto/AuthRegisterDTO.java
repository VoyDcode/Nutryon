package com.oracle.nutryon.web.dto;

import com.oracle.nutryon.domain.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AuthRegisterDTO(
        @NotBlank String nome,
        @NotBlank @Email String email,
        @NotBlank String senha,
        Role role
) {
}
