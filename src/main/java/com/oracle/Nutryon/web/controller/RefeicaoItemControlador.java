package com.oracle.nutryon.web.controller;

import com.oracle.nutryon.procedure.RefeicaoItemProcService;
import java.math.BigDecimal;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador responsável por manipular os itens de uma refeição via
 * stored procedures. Permite adicionar um novo item, atualizar a
 * quantidade de um item e removê-lo. As respostas incluem apenas o
 * link self, pois outros métodos retornam void.
 */
@RestController
@RequestMapping("/api/refeicoes/{idRefeicao}/itens")
public class RefeicaoItemControlador {

  private final RefeicaoItemProcService proc;

  public RefeicaoItemControlador(RefeicaoItemProcService proc) {
    this.proc = proc;
  }

  /**
   * DTO para criação de um novo item de refeição via procedure.
   */
  public static final class NovoItemDTO {
    public Long idIngrediente;
    public BigDecimal quantidade;
    public Long idUnidade;
  }

  /**
   * DTO para atualização de quantidade de item de refeição.
   */
  public static final class AtualizarQtdeDTO {
    public BigDecimal quantidade;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public EntityModel<Void> adicionar(
      @PathVariable long idRefeicao,
      @RequestBody NovoItemDTO dto
  ) {
    proc.insert(idRefeicao, dto.idIngrediente, dto.quantidade.doubleValue(), dto.idUnidade);
    return EntityModel.of(null,
        WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(RefeicaoItemControlador.class).adicionar(idRefeicao, dto)
        ).withSelfRel()
    );
  }

  @PutMapping("/{idIngrediente}")
  public EntityModel<Void> atualizarQuantidade(
      @PathVariable long idRefeicao,
      @PathVariable long idIngrediente,
      @RequestBody AtualizarQtdeDTO dto
  ) {
    proc.updateQuantity(idRefeicao, idIngrediente, dto.quantidade.doubleValue());
    return EntityModel.of(null,
        WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(RefeicaoItemControlador.class)
                .atualizarQuantidade(idRefeicao, idIngrediente, dto)
        ).withSelfRel()
    );
  }

  @DeleteMapping("/{idIngrediente}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void remover(@PathVariable long idRefeicao, @PathVariable long idIngrediente) {
    proc.deleteItem(idRefeicao, idIngrediente);
  }
}