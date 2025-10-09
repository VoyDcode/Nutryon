package com.oracle.nutryon.web.controller;

import com.oracle.nutryon.domain.entity.*;
import com.oracle.nutryon.repository.*;
import com.oracle.nutryon.service.ServicoNutricao;
import com.oracle.nutryon.web.controller.dto.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.IntStream;
import static org.springframework.format.annotation.DateTimeFormat.ISO;

@RestController
@RequestMapping("/api/refeicoes")
public class RefeicaoControlador {

  private final RefeicaoRepositorio refeicoes;
  private final UsuarioRepositorio usuarios;
  private final IngredienteRepositorio ingredientes;
  private final ServicoNutricao nutricao;

  public RefeicaoControlador(RefeicaoRepositorio r, UsuarioRepositorio u,
                             IngredienteRepositorio i, ServicoNutricao n) {
    this.refeicoes = r; this.usuarios = u; this.ingredientes = i; this.nutricao = n;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public TotaisMacrosDTO create(@Valid @RequestBody CriarRefeicaoDTO dto){
    var usuario = usuarios.findById(dto.usuarioId()).orElseThrow();
    var refeicao = new Refeicao();
    refeicao.setUsuario(usuario);
    refeicao.setData(dto.data());
    refeicao.setTipo(dto.tipo());

    dto.itens().forEach(i -> {
      var ing = ingredientes.findById(i.ingredienteId()).orElseThrow();
      var item = new ItemRefeicao();
      item.setRefeicao(refeicao);
      item.setIngrediente(ing);
      item.setQuantidade(i.quantidade());
      refeicao.getItens().add(item);
    });

    var salva = refeicoes.save(refeicao);
    return nutricao.calculateTotals(salva);
  }

  @GetMapping
  public List<RefeicaoViewDTO> list(
    @RequestParam(required = false) Long usuarioId,
    @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate data
  ){
    List<Refeicao> lista;
    if (usuarioId != null && data != null) {
      lista = refeicoes.findByUsuarioIdAndData(usuarioId, data);
    } else if (usuarioId != null) {
      lista = refeicoes.findByUsuarioId(usuarioId);
    } else if (data != null) {
      lista = refeicoes.findByData(data);
    } else {
      lista = refeicoes.findAll();
    }

    return lista.stream().map(r -> new RefeicaoViewDTO(
      r.getId(),
      r.getUsuario().getId(),
      r.getData(),
      r.getTipo(),
      r.getItens().stream().map(it -> new ItemRefeicaoViewDTO(
        it.getIngrediente().getId(),
        it.getIngrediente().getNome(),
        it.getQuantidade()
      )).toList()
    )).toList();
  }

  @GetMapping("/usuarios/{usuarioId}/dia/{data}")
  public TotaisMacrosDTO day(@PathVariable Long usuarioId,
                             @PathVariable @DateTimeFormat(iso = ISO.DATE) LocalDate data){
    var lista = refeicoes.findByUsuarioIdAndData(usuarioId, data);
    var agreg = new Refeicao();
    agreg.setItens(lista.stream().flatMap(r -> r.getItens().stream()).toList());
    return nutricao.calculateTotals(agreg);
  }

  @GetMapping("/usuarios/{usuarioId}/semana/{segunda}")
  public java.util.List<ResumoDiaDTO> week(@PathVariable Long usuarioId,
                                           @PathVariable @DateTimeFormat(iso = ISO.DATE) LocalDate segunda){
    return IntStream.range(0,7).mapToObj(i -> segunda.plusDays(i)).map(d -> {
      var lista = refeicoes.findByUsuarioIdAndData(usuarioId, d);
      var agreg = new Refeicao();
      agreg.setItens(lista.stream().flatMap(r -> r.getItens().stream()).toList());
      return new ResumoDiaDTO(d, nutricao.calculateTotals(agreg));
    }).toList();
  }
}
