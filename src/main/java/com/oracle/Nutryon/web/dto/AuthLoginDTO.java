package com.oracle.nutryon.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AuthLoginDTO(
        @NotBlank @Email String email,
        @NotBlank String senha
) {
}
