package com.oracle.nutryon.web.controller.dto;

import com.oracle.nutryon.domain.enums.UnidadeBase;
import java.math.BigDecimal;

public record CriarIngredienteDTO(
  String nome,
  UnidadeBase unidadeBase,
  BigDecimal kcalPor100,
  BigDecimal proteinaPor100,
  BigDecimal carboPor100,
  BigDecimal gorduraPor100
) {}
