package com.oracle.nutryon.web.controller.dto;

import com.oracle.nutryon.domain.enums.TipoRefeicao;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

public record CriarRefeicaoDTO(
  @NotNull Long usuarioId,
  @NotNull LocalDate data,
  @NotNull TipoRefeicao tipo,
  @NotNull @Size(min = 1) List<@Valid ItemRefeicaoDTO> itens
) {}
