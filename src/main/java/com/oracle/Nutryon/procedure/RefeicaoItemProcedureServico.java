package com.oracle.nutryon.procedure;

import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;

@Service
public class RefeicaoItemProcedureServico {
  private final SimpleJdbcCall insertCall, updateQtyCall, deleteCall;

  public RefeicaoItemProcedureServico(JdbcTemplate jdbc) {
    this.insertCall   = new SimpleJdbcCall(jdbc).withProcedureName("PRC_REFEICAO_ITEM_INS");
    this.updateQtyCall= new SimpleJdbcCall(jdbc).withProcedureName("PRC_REFEICAO_ITEM_UPD_QTDE");
    this.deleteCall   = new SimpleJdbcCall(jdbc).withProcedureName("PRC_REFEICAO_ITEM_DEL");
  }

  public void insert(long mealId, long ingredientId, double quantity, long unitId) {
    insertCall.execute(Map.of("p_id_refeicao", mealId, "p_id_ingrediente", ingredientId,
                              "p_qtde", quantity, "p_id_unidade", unitId));
  }

  public void updateQuantity(long mealId, long ingredientId, double quantity) {
    updateQtyCall.execute(Map.of("p_id_refeicao", mealId, "p_id_ingrediente", ingredientId, "p_qtde", quantity));
  }

  public void deleteItem(long mealId, long ingredientId) {
    deleteCall.execute(Map.of("p_id_refeicao", mealId, "p_id_ingrediente", ingredientId));
  }
}
