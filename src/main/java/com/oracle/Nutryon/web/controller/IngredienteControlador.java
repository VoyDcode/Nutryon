package com.oracle.nutryon.web.controller;

import com.oracle.nutryon.service.IngredienteService;
import com.oracle.nutryon.web.controller.dto.CriarIngredienteDTO;
import com.oracle.nutryon.web.controller.dto.IngredienteViewDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/ingredientes")
public class IngredienteControlador {

  private final IngredienteService service;

  public IngredienteControlador(IngredienteService service) { this.service = service; }

  @PostMapping
  public ResponseEntity<IngredienteViewDTO> create(@Valid @RequestBody CriarIngredienteDTO dto) {
    IngredienteViewDTO criado = service.create(dto);
    URI location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(criado.id())
            .toUri();
    return ResponseEntity.created(location).body(criado);
  }

  @GetMapping
  public List<IngredienteViewDTO> findAll(){ return service.findAll(); }
}
