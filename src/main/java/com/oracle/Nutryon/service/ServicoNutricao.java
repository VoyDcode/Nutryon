package com.oracle.nutryon.service;

import com.oracle.nutryon.domain.entity.ItemRefeicao;
import com.oracle.nutryon.domain.entity.Refeicao;
import com.oracle.nutryon.web.controller.dto.TotaisMacrosDTO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import static java.math.BigDecimal.ZERO;
import static java.math.BigDecimal.valueOf;
import static java.math.RoundingMode.HALF_UP;

@Service
public class ServicoNutricao {

  public TotaisMacrosDTO calculateTotals(Refeicao refeicao) {
    BigDecimal kcal = ZERO, prot = ZERO, carb = ZERO, gord = ZERO;

    for (ItemRefeicao it : refeicao.getItens()) {
      var ing = it.getIngrediente();
      var fator = it.getQuantidade().divide(valueOf(100), 6, HALF_UP);
      kcal = kcal.add(ing.getKcalPor100().multiply(fator));
      prot = prot.add(ing.getProteinaPor100().multiply(fator));
      carb = carb.add(ing.getCarboPor100().multiply(fator));
      gord = gord.add(ing.getGorduraPor100().multiply(fator));
    }
    return new TotaisMacrosDTO(scale(kcal), scale(prot), scale(carb), scale(gord));
  }

  private BigDecimal scale(BigDecimal v) { return v.setScale(2, HALF_UP); }
}
