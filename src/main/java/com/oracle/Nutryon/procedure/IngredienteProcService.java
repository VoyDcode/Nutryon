package com.oracle.nutryon.procedure;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class IngredienteProcService {

    private final SimpleJdbcCall prcInserir;
    private final SimpleJdbcCall prcAtualizar;
    private final SimpleJdbcCall prcExcluir;

    public IngredienteProcService(JdbcTemplate jdbc) {
        this.prcInserir  = new SimpleJdbcCall(jdbc).withProcedureName("PRC_INGREDIENTE_INS");
        this.prcAtualizar= new SimpleJdbcCall(jdbc).withProcedureName("PRC_INGREDIENTE_ATUALIZAR");
        this.prcExcluir  = new SimpleJdbcCall(jdbc).withProcedureName("PRC_INGREDIENTE_EXCLUIR");
    }

    public long insert(String nome, Long categoriaId) {
        var in = new MapSqlParameterSource()
            .addValue("P_NOME", nome)
            .addValue("P_CATEGORIA_ID", categoriaId);
        Map<String, Object> out = prcInserir.execute(in);
        Object id = out.get("P_ID");
        if (id instanceof Number) return ((Number) id).longValue();
        return 0L;
    }

    public void update(long id, String nome, Long categoriaId) {
        var in = new MapSqlParameterSource()
            .addValue("P_ID", id)
            .addValue("P_NOME", nome)
            .addValue("P_CATEGORIA_ID", categoriaId);
        prcAtualizar.execute(in);
    }

    public void deleteItem(long id) {
        var in = new MapSqlParameterSource().addValue("P_ID", id);
        prcExcluir.execute(in);
    }
}
