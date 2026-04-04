package com.oracle.nutryon.web.controller;

import java.math.BigDecimal;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oracle.nutryon.procedure.RefeicaoItemProcedureServico;

/**
 * Controlador que expõe endpoints para manipulação de itens de refeição
 * por meio de procedures armazenadas em Oracle. Permite inserir,
 * atualizar a quantidade e remover itens de uma refeição.
 */
@RestController
@RequestMapping("/api/proc/refeicoes")
public class RefeicaoItemProcedureControlador {

  private final RefeicaoItemProcedureServico service;

  public RefeicaoItemProcedureControlador(RefeicaoItemProcedureServico service) {
    this.service = service;
  }

  /**
   * DTO de entrada para inserir ou atualizar um item de refeição via
   * procedure. Contém o identificador do ingrediente, a quantidade e a
   * unidade de medida.
   *
   * @param idIngrediente identificador do ingrediente
   * @param qtde quantidade do ingrediente
   * @param idUnidade identificador da unidade (ex.: gramas)
   */
  public record ItemProcDTO(Long idIngrediente, BigDecimal qtde, Long idUnidade) {}

  /**
   * Insere um item em uma refeição chamando a procedure
   * PRC_REFEICAO_ITEM_INS. Requer id da refeição no path.
   *
   * @param id id da refeição
   * @param in dados do item
   * @return modelo vazio com links de update/delete
   */
  @PostMapping("/{id}/itens")
  public EntityModel<Void> add(@PathVariable long id, @RequestBody ItemProcDTO in) {
    service.insert(id, in.idIngrediente(), in.qtde().doubleValue(), in.idUnidade());
    return EntityModel.of(null,
        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RefeicaoItemProcedureControlador.class)
            .add(id, in)).withSelfRel(),
        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RefeicaoItemProcedureControlador.class)
            .update(id, in.idIngrediente(), in)).withRel("update"),
        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RefeicaoItemProcedureControlador.class)
            .delete(id, in.idIngrediente())).withRel("delete"));
  }

  /**
   * Atualiza a quantidade de um item de refeição chamando
   * PRC_REFEICAO_ITEM_UPD_QTDE. Requer id da refeição e id do
   * ingrediente no path.
   *
   * @param id id da refeição
   * @param idIng id do ingrediente
   * @param in dados do item (apenas qtde é usada)
   * @return modelo vazio com links
   */
  @PutMapping("/{id}/itens/{idIng}")
  public EntityModel<Void> update(@PathVariable long id, @PathVariable long idIng, @RequestBody ItemProcDTO in) {
    service.updateQuantity(id, idIng, in.qtde().doubleValue());
    return EntityModel.of(null,
        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RefeicaoItemProcedureControlador.class)
            .update(id, idIng, in)).withSelfRel(),
        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RefeicaoItemProcedureControlador.class)
            .delete(id, idIng)).withRel("delete"));
  }

  /**
   * Remove um item de uma refeição chamando a procedure
   * PRC_REFEICAO_ITEM_DEL. Requer id da refeição e id do ingrediente no path.
   *
   * @param id id da refeição
   * @param idIng id do ingrediente
   * @return modelo vazio com link self
   */
  @DeleteMapping("/{id}/itens/{idIng}")
  public EntityModel<Void> delete(@PathVariable long id, @PathVariable long idIng) {
    service.deleteItem(id, idIng);
    return EntityModel.of(null,
        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RefeicaoItemProcedureControlador.class)
            .delete(id, idIng)).withSelfRel());
  }
}