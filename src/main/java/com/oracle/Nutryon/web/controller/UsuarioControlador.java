package com.oracle.nutryon.web.controller;

import com.oracle.nutryon.service.UsuarioService;
import com.oracle.nutryon.web.controller.dto.CriarUsuarioDTO;
import com.oracle.nutryon.web.controller.dto.UsuarioViewDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioControlador {

  private final UsuarioService service;

  public UsuarioControlador(UsuarioService service) { this.service = service; }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public UsuarioViewDTO create(@Valid @RequestBody CriarUsuarioDTO dto){
    return service.create(dto);
  }

  @GetMapping
  public List<UsuarioViewDTO> findAll(){ return service.findAll(); }
}


