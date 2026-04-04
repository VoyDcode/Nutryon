package com.oracle.nutryon.web.controller.dto;

import com.oracle.nutryon.domain.entity.Ingrediente;
import com.oracle.nutryon.domain.enums.UnidadeBase;
import java.math.BigDecimal;

public record IngredienteViewDTO(
    Long id,
    String nome,
    UnidadeBase unidadeBase,
    BigDecimal kcalPor100,
    BigDecimal proteinaPor100,
    BigDecimal carboPor100,
    BigDecimal gorduraPor100
) {
    public IngredienteViewDTO(Ingrediente ing) {
        this(
            ing.getId(),
            ing.getNome(),
            ing.getUnidadeBase(),
            ing.getKcalPor100(),
            ing.getProteinaPor100(),
            ing.getCarboPor100(),
            ing.getGorduraPor100()
        );
    }
}
