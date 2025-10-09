package com.oracle.nutryon.web.controller.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record ItemRefeicaoDTO(
  @NotNull Long ingredienteId,
  @NotNull @DecimalMin("0.01") BigDecimal quantidade
) {}
