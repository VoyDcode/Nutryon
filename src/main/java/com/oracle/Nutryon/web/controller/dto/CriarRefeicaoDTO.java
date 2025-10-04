package com.oracle.nutryon.web.controller.dto;

import com.oracle.nutryon.domain.enums.TipoRefeicao;
import java.time.LocalDate;
import java.util.List;

public record CriarRefeicaoDTO(
  Long usuarioId,
  LocalDate data,
  TipoRefeicao tipo,
  List<ItemRefeicaoDTO> itens
) {}
