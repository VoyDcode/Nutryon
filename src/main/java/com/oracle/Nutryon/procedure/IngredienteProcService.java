package br.com.nutryon.procedure;

import java.sql.Types;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;

@Service
public class IngredienteProcService {
  private final SimpleJdbcCall insertCall, updateCall, deleteCall;

  public IngredienteProcService(JdbcTemplate jdbc) {
    this.insertCall = new SimpleJdbcCall(jdbc)
        .withProcedureName("PRC_INGREDIENTE_INS")
        .declareParameters(new SqlOutParameter("p_id_out", Types.NUMERIC));
    this.updateCall = new SimpleJdbcCall(jdbc).withProcedureName("PRC_INGREDIENTE_UPD");
    this.deleteCall = new SimpleJdbcCall(jdbc).withProcedureName("PRC_INGREDIENTE_DEL");
  }

  public long insert(String name, Long categoryId) {
    var out = insertCall.execute(Map.of(
      "p_nome", name,
      "p_id_categoria", categoryId
    ));
    return ((Number) out.get("p_id_out")).longValue();
  }

  public void update(long id, String name, Long categoryId) {
    updateCall.execute(Map.of(
      "p_id", id,
      "p_nome", name,
      "p_id_categoria", categoryId
    ));
  }

  public void deleteItem(long id) {
    deleteCall.execute(Map.of("p_id", id));
  }
}
