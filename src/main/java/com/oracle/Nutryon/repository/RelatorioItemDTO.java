package com.oracle.nutryon.repository;

import java.math.BigDecimal;
import java.time.Instant;

public record RelatorioItemDTO(
  Long idRefeicao, Instant dtRef, String tipo,
  BigDecimal kcal, BigDecimal prot, BigDecimal carb, BigDecimal gord) {}
