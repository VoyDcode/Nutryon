package com.oracle.nutryon.web.controller;

import com.oracle.nutryon.service.UsuarioService;
import com.oracle.nutryon.web.controller.dto.CriarUsuarioDTO;
import com.oracle.nutryon.web.controller.dto.UsuarioViewDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioControlador {

  private final UsuarioService service;

  public UsuarioControlador(UsuarioService service) { this.service = service; }

  @PostMapping
  public ResponseEntity<UsuarioViewDTO> create(@Valid @RequestBody CriarUsuarioDTO dto) {
    UsuarioViewDTO criado = service.create(dto);
    URI location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(criado.id())
            .toUri();
    return ResponseEntity.created(location).body(criado);
  }

  @GetMapping
  public List<UsuarioViewDTO> findAll(){ return service.findAll(); }
}


