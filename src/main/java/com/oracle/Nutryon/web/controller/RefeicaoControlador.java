import com.oracle.nutryon.web.controller.dto.TotaisMacrosDTO;

@PostMapping
public TotaisMacrosDTO create(@Valid @RequestBody CriarRefeicaoDTO dto){
  // ...
  var salva = refeicoes.save(refeicao);
  return nutricao.calculateTotals(salva);
}

@GetMapping("/usuarios/{usuarioId}/dia/{data}")
public TotaisMacrosDTO day(@PathVariable Long usuarioId,
                           @PathVariable @DateTimeFormat(iso = ISO.DATE) LocalDate data){
  var lista = refeicoes.findByUsuarioIdAndData(usuarioId, data);
  var agreg = new Refeicao();
  agreg.setItens(lista.stream().flatMap(r -> r.getItens().stream()).toList());
  return nutricao.calculateTotals(agreg);
}
