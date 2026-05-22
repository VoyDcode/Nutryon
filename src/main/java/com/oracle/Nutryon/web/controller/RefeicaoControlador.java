package com.oracle.nutryon.web.controller;

import com.oracle.nutryon.domain.entity.Usuario;
import com.oracle.nutryon.service.RefeicaoService;
import com.oracle.nutryon.web.controller.dto.*;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.time.LocalDate;
import static org.springframework.format.annotation.DateTimeFormat.ISO;

@RestController
@RequestMapping("/api/refeicoes")
public class RefeicaoControlador {

  private final RefeicaoService service;

  public RefeicaoControlador(RefeicaoService service) {
    this.service = service;
  }

  @PostMapping
  public ResponseEntity<RefeicaoCriadaDTO> create(@Valid @RequestBody CriarRefeicaoDTO dto,
                                                   @AuthenticationPrincipal Usuario usuario) {
    RefeicaoCriadaDTO criado = service.create(dto, usuario.getId());
    URI location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(criado.id())
            .toUri();
    return ResponseEntity.created(location).body(criado);
  }

  @GetMapping
  public List<RefeicaoViewDTO> list(
    @AuthenticationPrincipal Usuario usuario,
    @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate data
  ){
    return service.list(usuario.getId(), data);
  }

  @GetMapping("/resumo-diario/{data}")
  public TotaisMacrosDTO day(@AuthenticationPrincipal Usuario usuario,
                             @PathVariable @DateTimeFormat(iso = ISO.DATE) LocalDate data){
    return service.day(usuario.getId(), data);
  }

  @GetMapping("/resumo-semanal/{segunda}")
  public List<ResumoDiaDTO> week(@AuthenticationPrincipal Usuario usuario,
                                 @PathVariable @DateTimeFormat(iso = ISO.DATE) LocalDate segunda){
    return service.week(usuario.getId(), segunda);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id,
                                     @AuthenticationPrincipal Usuario usuario) {
    service.delete(id, usuario.getId());
    return ResponseEntity.noContent().build();
  }
}
