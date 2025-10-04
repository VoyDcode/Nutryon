package com.oracle.nutryon.web.controller.dto;

import java.math.BigDecimal;

public record TotaisMacrosDTO(

  BigDecimal kcal,
  BigDecimal proteina,
  BigDecimal carbo,
  BigDecimal gordura
) {
    
}
