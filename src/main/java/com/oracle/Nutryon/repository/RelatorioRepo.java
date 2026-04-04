package com.oracle.nutryon.repository;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

/**
 * Repositório para consultas ao relatório de refeições agregadas por usuário e dia.
 * Este repositório utiliza uma função pipelined definida no banco Oracle
 * (FN_REL_REFEICOES_USUARIO) que retorna uma tabela de objetos, permitindo
 * a execução via native query e o mapeamento manual para um DTO.
 */
@Repository
public class RelatorioRepo {

  /**
   * DTO que representa um item agregado do relatório. Cada item contém a
   * refeição, data/hora e os totais de macronutrientes calculados pela função
   * pipelined.
   *
   * @param idRefeicao identificador da refeição
   * @param dataHora data e hora da refeição
   * @param tipo tipo da refeição (ex.: ALMOCO)
   * @param kcalTotal soma das calorias
   * @param protTotal soma de proteínas
   * @param carbTotal soma de carboidratos
   * @param gordTotal soma de gorduras
   */
  public record RelatorioItemDTO(
      Long idRefeicao,
      Instant dataHora,
      String tipo,
      BigDecimal kcalTotal,
      BigDecimal protTotal,
      BigDecimal carbTotal,
      BigDecimal gordTotal
  ) {}

  @PersistenceContext
  private EntityManager em;

  /**
   * Consulta a função pipelined FN_REL_REFEICOES_USUARIO passando o
   * identificador do usuário e a data. Caso a data seja nula, a função
   * considera o dia atual no banco. O resultado é mapeado para
   * {@link RelatorioItemDTO} de forma manual.
   *
   * @param usuarioId identificador do usuário
   * @param dia data desejada (sem hora)
   * @return lista de itens do relatório
   */
  public List<RelatorioItemDTO> listarPorUsuarioEDia(Long usuarioId, LocalDate dia) {
    var nativeQuery = """
      SELECT ID_REFEICAO, DT_REF, TIPO_REFEICAO, KCAL_TOTAL, PROT_TOTAL, CARB_TOTAL, GORD_TOTAL
      FROM TABLE(FN_REL_REFEICOES_USUARIO(:p_usuario_id, :p_dia))
    """;
    var query = em.createNativeQuery(nativeQuery)
        .setParameter("p_usuario_id", usuarioId)
        .setParameter("p_dia", java.sql.Date.valueOf(dia));

    @SuppressWarnings("unchecked")
    List<Object[]> rows = query.getResultList();
    return rows.stream().map(r -> new RelatorioItemDTO(
        ((Number) r[0]).longValue(),
        ((Timestamp) r[1]).toInstant(),
        (String) r[2],
        (BigDecimal) r[3],
        (BigDecimal) r[4],
        (BigDecimal) r[5],
        (BigDecimal) r[6]
    )).toList();
  }
}
