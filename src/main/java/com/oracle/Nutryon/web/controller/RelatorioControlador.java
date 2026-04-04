package com.oracle.nutryon.web.controller;

import com.oracle.nutryon.repository.RelatorioRepo;
import com.oracle.nutryon.repository.RelatorioRepo.RelatorioItemDTO;
import java.time.LocalDate;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador que expõe o relatório de refeições para um usuário em um dia
 * específico. Este endpoint delega a consulta para {@link RelatorioRepo}
 * e retorna uma coleção de itens com links HATEOAS.
 */
@RestController
@RequestMapping("/api/usuarios/{usuarioId}/relatorio")
public class RelatorioControlador {

  private final RelatorioRepo repo;

  public RelatorioControlador(RelatorioRepo repo) {
    this.repo = repo;
  }

  /**
   * Retorna o relatório agregando os macronutrientes consumidos por um
   * usuário em um determinado dia. O parâmetro dia deve estar no formato
   * ISO (yyyy-MM-dd).
   *
   * @param usuarioId identificador do usuário
   * @param dia data a ser consultada (obrigatória)
   * @return coleção de itens de relatório com links
   */
  @GetMapping
  public CollectionModel<EntityModel<RelatorioItemDTO>> porDia(
      @PathVariable Long usuarioId,
      @RequestParam @DateTimeFormat(iso = ISO.DATE) LocalDate dia
  ) {
    List<RelatorioItemDTO> itens = repo.listarPorUsuarioEDia(usuarioId, dia);
    var models = itens.stream().map(item -> EntityModel.of(
        item,
        WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(RelatorioControlador.class).porDia(usuarioId, dia)
        ).withSelfRel()
    )).toList();
    return CollectionModel.of(
        models,
        WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(RelatorioControlador.class).porDia(usuarioId, dia)
        ).withSelfRel()
    );
  }
}