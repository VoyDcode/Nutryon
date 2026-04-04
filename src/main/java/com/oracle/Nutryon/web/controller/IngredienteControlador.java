package com.oracle.nutryon.web.controller;

import com.oracle.nutryon.service.IngredienteService;
import com.oracle.nutryon.web.controller.dto.CriarIngredienteDTO;
import com.oracle.nutryon.web.controller.dto.IngredienteViewDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/ingredientes")
public class IngredienteControlador {

  private final IngredienteService service;
  
  public IngredienteControlador(IngredienteService service) { this.service = service; }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public IngredienteViewDTO create(@Valid @RequestBody CriarIngredienteDTO dto){
    return service.create(dto);
  }

  @GetMapping
  public List<IngredienteViewDTO> findAll(){ return service.findAll(); }
}
