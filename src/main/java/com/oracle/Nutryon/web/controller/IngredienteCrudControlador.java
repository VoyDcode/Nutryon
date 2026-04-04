package com.oracle.nutryon.web.controller;

import com.oracle.nutryon.procedure.IngredienteProcService;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador dedicado às operações de atualização e exclusão de ingredientes
 * via procedures armazenadas no banco de dados. Este controlador demonstra o
 * uso das stored procedures PRC_INGREDIENTE_UPD e PRC_INGREDIENTE_DEL e
 * responde com modelos HATEOAS contendo links de navegação.
 */
@RestController
@RequestMapping("/api/ingredientes")
public class IngredienteCrudControlador {

  private final IngredienteProcService proc;

  public IngredienteCrudControlador(IngredienteProcService proc) {
    this.proc = proc;
  }

  /**
   * DTO para atualização de ingrediente via procedure. A procedure de
   * atualização exige apenas o nome e o identificador da categoria.
   */
  public static final class AtualizarIngredienteDTO {
    public String nome;
    public Long categoriaId;
  }

  /**
   * Atualiza um ingrediente existente chamando a procedure
   * PRC_INGREDIENTE_UPD. Retorna modelo vazio com link self. Outros links
   * (delete) são omitidos para evitar uso de linkTo com retorno void.
   *
   * @param id identificador do ingrediente
   * @param dto dados de atualização
   * @return modelo vazio com link self
   */
  @PutMapping("/{id}")
  public EntityModel<Void> atualizar(
      @PathVariable Long id,
      @RequestBody AtualizarIngredienteDTO dto
  ) {
    proc.update(id, dto.nome, dto.categoriaId);
    return EntityModel.of(null,
        WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(IngredienteCrudControlador.class).atualizar(id, dto)
        ).withSelfRel()
    );
  }

  /**
   * Exclui um ingrediente chamando PRC_INGREDIENTE_DEL. Retorna 204 sem
   * conteúdo.
   *
   * @param id identificador do ingrediente
   */
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void excluir(@PathVariable Long id) {
    proc.deleteItem(id);
  }
}