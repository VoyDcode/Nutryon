package com.oracle.nutryon.web.controller.dto;

import java.math.BigDecimal;

public record ItemRefeicaoDTO(
  Long ingredienteId,
  BigDecimal quantidade
) {}
