package com.oracle.nutryon.web.controller.dto;

import jakarta.validation.constraints.*;

public record CriarUsuarioDTO(
  @NotBlank String nome,
  @Email @NotBlank String email
) {}
