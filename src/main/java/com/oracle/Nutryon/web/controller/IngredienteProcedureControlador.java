package com.oracle.nutryon.web.controller;

import com.oracle.nutryon.procedure.IngredienteProcService;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
  Controlador que expõe endpoints para manipulação de ingredientes via
  procedures armazenadas em Oracle. Esses endpoints exercitam as
  procedures PRC_INGREDIENTE_INS, PRC_INGREDIENTE_UPD e
  PRC_INGREDIENTE_DEL. */
  
@RestController
@RequestMapping("/api/proc/ingredientes")
public class IngredienteProcedureControlador {

  private final IngredienteProcService service;

  public IngredienteProcedureControlador(IngredienteProcService service) {
    this.service = service;
  }

  /**
    DTO de entrada para criação/atualização de ingrediente via procedure.
    Somente nome e categoria são exigidos pela procedure simplificada.
   
    @param nome nome do ingrediente
    @param categoriaId identificador da categoria do ingrediente */
    
  public record IngredienteProcDTO(String nome, Long categoriaId) {}

  /**
    Insere um novo ingrediente chamando a procedure PRC_INGREDIENTE_INS.
   
    @param in informações do ingrediente
    @return modelo com o id gerado e links de update/delete
   */
  @PostMapping
  public EntityModel<Long> create(@RequestBody IngredienteProcDTO in) {
    long id = service.insert(in.nome(), in.categoriaId());
    return EntityModel.of(id,
        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(IngredienteProcedureControlador.class)
            .create(in)).withSelfRel(),
        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(IngredienteProcedureControlador.class)
            .update(id, in)).withRel("update"),
        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(IngredienteProcedureControlador.class)
            .delete(id)).withRel("delete"));
  }

  /**
    Atualiza um ingrediente chamando a procedure PRC_INGREDIENTE_UPD.
   
    @param id id do ingrediente
    @param in dados para atualização
    @return modelo vazio com links
   */
  @PutMapping("/{id}")
  public EntityModel<Void> update(@PathVariable long id, @RequestBody IngredienteProcDTO in) {
    service.update(id, in.nome(), in.categoriaId());
    return EntityModel.of(null,
        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(IngredienteProcedureControlador.class)
            .update(id, in)).withSelfRel(),
        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(IngredienteProcedureControlador.class)
            .delete(id)).withRel("delete"));
  }

  /**
    Exclui um ingrediente chamando a procedure PRC_INGREDIENTE_DEL.
   
    @param id identificador do ingrediente
    @return modelo vazio com link self

   */
  @DeleteMapping("/{id}")
  public EntityModel<Void> delete(@PathVariable long id) {
    service.deleteItem(id);
    return EntityModel.of(null,
        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(IngredienteProcedureControlador.class)
            .delete(id)).withSelfRel());
  }
}