package com.oracle.nutryon.web.controller.dto;

import java.time.LocalDate;

public record ResumoDiaDTO(
  LocalDate data,
  TotaisMacrosDTO totais
) {

}
