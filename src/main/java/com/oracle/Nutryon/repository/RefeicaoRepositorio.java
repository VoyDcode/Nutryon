package com.oracle.nutryon.repository;

import com.oracle.nutryon.domain.entity.Refeicao;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface RefeicaoRepositorio extends JpaRepository<Refeicao, Long> {
  List<Refeicao> findByUsuarioIdAndData(Long usuarioId, LocalDate data);
  List<Refeicao> findByUsuarioIdAndDataBetween(Long usuarioId, LocalDate inicio, LocalDate fim);
  List<Refeicao> findByUsuarioId(Long usuarioId);
  List<Refeicao> findByData(LocalDate data);
}
