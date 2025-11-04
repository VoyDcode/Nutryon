package com.oracle.nutryon.repository;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

record RelatorioItemDTO(
  Long idRefeicao, Instant dtRef, String tipo,
  BigDecimal kcal, BigDecimal prot, BigDecimal carb, BigDecimal gord) {}

@Repository
public class RelatorioRepo {
  @PersistenceContext EntityManager em;

  @SuppressWarnings("unchecked")
  public List<RelatorioItemDTO> listByUserAndDay(long userId, LocalDate day){
    var q = em.createNativeQuery("""
      SELECT ID_REFEICAO, DT_REF, TIPO_REFEICAO, KCAL_TOTAL, PROT_TOTAL, CARB_TOTAL, GORD_TOTAL
      FROM TABLE(FN_REL_REFEICOES_USUARIO(:id, :dia))
    """);
    q.setParameter("id", userId);
    q.setParameter("dia", java.sql.Date.valueOf(day));
    var rows = (List<Object[]>) q.getResultList();
    return rows.stream().map(r -> new RelatorioItemDTO(
      ((Number) r[0]).longValue(),
      ((Timestamp) r[1]).toInstant(),
      (String) r[2],
      (BigDecimal) r[3], (BigDecimal) r[4], (BigDecimal) r[5], (BigDecimal) r[6]
    )).toList();
  }
}
