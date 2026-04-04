package com.oracle.nutryon.web.controller;

import com.oracle.nutryon.service.RefeicaoService;
import com.oracle.nutryon.web.controller.dto.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.time.LocalDate;
import static org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.transaction.annotation.Transactional;

@RestController
@RequestMapping("/api/refeicoes")
public class RefeicaoControlador {

  private final RefeicaoService service;

  public RefeicaoControlador(RefeicaoService service) {
    this.service = service;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public TotaisMacrosDTO create(@Valid @RequestBody CriarRefeicaoDTO dto){
    return service.create(dto);
  }

  @GetMapping
  public List<RefeicaoViewDTO> list(
    @RequestParam(required = false) Long usuarioId,
    @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate data
  ){
    return service.list(usuarioId, data);
  }

  @GetMapping("/usuarios/{usuarioId}/dia/{data}")
  public TotaisMacrosDTO day(@PathVariable Long usuarioId,
                             @PathVariable @DateTimeFormat(iso = ISO.DATE) LocalDate data){
    return service.day(usuarioId, data);
  }

  @GetMapping("/usuarios/{usuarioId}/semana/{segunda}")
  public List<ResumoDiaDTO> week(@PathVariable Long usuarioId,
                                 @PathVariable @DateTimeFormat(iso = ISO.DATE) LocalDate segunda){
    return service.week(usuarioId, segunda);
  }
}
