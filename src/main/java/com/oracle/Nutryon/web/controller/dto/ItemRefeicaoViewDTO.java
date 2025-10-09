package com.oracle.nutryon.web.controller.dto;

import java.math.BigDecimal;

public record ItemRefeicaoViewDTO(
  Long ingredienteId,
  String ingredienteNome,
  BigDecimal quantidade
) {}


