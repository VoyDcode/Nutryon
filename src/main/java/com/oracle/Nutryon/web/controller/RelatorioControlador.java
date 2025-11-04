package com.oracle.nutryon.web.controller;

import com.oracle.nutryon.repository.RelatorioRepo;
import com.oracle.nutryon.repository.RelatorioItemDTO;
import java.time.LocalDate;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/* End point para consulta do relatório de refeições de um usuário. Esse
    relatório corresponde à função pipelined {@code FN_REL_REFEICOES_USUARIO},
    retornando para cada refeição do dia os totais de calorias e
    macronutrientes. */
    
@RestController
@RequestMapping("/api/usuarios")
public class RelatorioControlador {

  private final RelatorioRepo repo;

  public RelatorioControlador(RelatorioRepo repo) {
    this.repo = repo;
  }

/*  Consulta o relatório de refeições de um usuário para o dia
  especificado. Se nenhum dia for informado, utiliza a data atual.*/

  @GetMapping("/{id}/relatorio")
  public CollectionModel<EntityModel<RelatorioItemDTO>> report(
      @PathVariable long id,
      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dia) {
    List<RelatorioItemDTO> items = repo.listByUserAndDay(id, dia);

    // Mapeia cada item para EntityModel com link de self
    List<EntityModel<RelatorioItemDTO>> models = items.stream()
        .map(item -> EntityModel.of(item,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RelatorioControlador.class)
                .report(id, dia)).withSelfRel()))
        .toList();
    return CollectionModel.of(models,
        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RelatorioControlador.class)
            .report(id, dia)).withSelfRel());
  }
}