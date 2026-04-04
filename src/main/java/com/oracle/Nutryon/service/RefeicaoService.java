package com.oracle.nutryon.service;

import com.oracle.nutryon.domain.entity.ItemRefeicao;
import com.oracle.nutryon.domain.entity.Refeicao;
import com.oracle.nutryon.repository.IngredienteRepositorio;
import com.oracle.nutryon.repository.RefeicaoRepositorio;
import com.oracle.nutryon.repository.UsuarioRepositorio;
import com.oracle.nutryon.web.controller.dto.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class RefeicaoService {

    private final RefeicaoRepositorio refeicoes;
    private final UsuarioRepositorio usuarios;
    private final IngredienteRepositorio ingredientes;
    private final ServicoNutricao nutricao;

    public RefeicaoService(RefeicaoRepositorio r, UsuarioRepositorio u,
                           IngredienteRepositorio i, ServicoNutricao n) {
        this.refeicoes = r;
        this.usuarios = u;
        this.ingredientes = i;
        this.nutricao = n;
    }

    @Transactional
    public TotaisMacrosDTO create(CriarRefeicaoDTO dto) {
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

    @Transactional(readOnly = true)
    public List<RefeicaoViewDTO> list(Long usuarioId, LocalDate data) {
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

    @Transactional(readOnly = true)
    public TotaisMacrosDTO day(Long usuarioId, LocalDate data) {
        var lista = refeicoes.findByUsuarioIdAndData(usuarioId, data);
        var agreg = new Refeicao();
        agreg.setItens(lista.stream().flatMap(r -> r.getItens().stream()).toList());
        return nutricao.calculateTotals(agreg);
    }

    @Transactional(readOnly = true)
    public List<ResumoDiaDTO> week(Long usuarioId, LocalDate segunda) {
        LocalDate domingo = segunda.plusDays(6);
        
        // Single optimized query (No N+1)
        List<Refeicao> refeicoesDaSemana = refeicoes.findByUsuarioIdAndDataBetween(usuarioId, segunda, domingo);

        // Group by Date for fast processing
        Map<LocalDate, List<Refeicao>> refeicoesPorDia = refeicoesDaSemana.stream()
                .collect(Collectors.groupingBy(Refeicao::getData));

        return IntStream.range(0, 7).mapToObj(segunda::plusDays).map(dia -> {
            var listaDoDia = refeicoesPorDia.getOrDefault(dia, List.of());
            var agreg = new Refeicao();
            agreg.setItens(listaDoDia.stream().flatMap(r -> r.getItens().stream()).toList());
            return new ResumoDiaDTO(dia, nutricao.calculateTotals(agreg));
        }).toList();
    }
}
