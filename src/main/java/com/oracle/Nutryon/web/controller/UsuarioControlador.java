package com.oracle.nutryon.web.controller;

import com.oracle.nutryon.domain.entity.Usuario;
import com.oracle.nutryon.repository.UsuarioRepositorio;
import com.oracle.nutryon.web.controller.dto.CriarUsuarioDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioControlador {

  private final UsuarioRepositorio repo;

  public UsuarioControlador(UsuarioRepositorio repo) { this.repo = repo; }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Usuario create(@Valid @RequestBody CriarUsuarioDTO dto){
    var u = new Usuario();
    u.setNome(dto.nome());
    u.setEmail(dto.email());
    return repo.save(u);
  }

  @GetMapping
  public List<Usuario> findAll(){ return repo.findAll(); }
}


