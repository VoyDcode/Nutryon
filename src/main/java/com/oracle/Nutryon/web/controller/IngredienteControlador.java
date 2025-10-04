package com.oracle.nutryon.web.controller;

import com.oracle.nutryon.domain.entity.Ingrediente;
import com.oracle.nutryon.repository.IngredienteRepositorio;
import com.oracle.nutryon.web.controller.dto.CriarIngredienteDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/ingredientes")
public class IngredienteControlador {

  private final IngredienteRepositorio repo;
  public IngredienteControlador(IngredienteRepositorio repo) { this.repo = repo; }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Ingrediente create(@Valid @RequestBody CriarIngredienteDTO dto){
    var ing = new Ingrediente();
    ing.setNome(dto.nome());
    ing.setUnidadeBase(dto.unidadeBase());
    ing.setKcalPor100(dto.kcalPor100());
    ing.setProteinaPor100(dto.proteinaPor100());
    ing.setCarboPor100(dto.carboPor100());
    ing.setGorduraPor100(dto.gorduraPor100());
    return repo.save(ing);
  }

  @GetMapping
  public List<Ingrediente> findAll(){ return repo.findAll(); }
}
