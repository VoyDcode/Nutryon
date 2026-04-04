package com.oracle.nutryon.service;

import com.oracle.nutryon.domain.entity.Ingrediente;
import com.oracle.nutryon.repository.IngredienteRepositorio;
import com.oracle.nutryon.web.controller.dto.CriarIngredienteDTO;
import com.oracle.nutryon.web.controller.dto.IngredienteViewDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class IngredienteService {

    private final IngredienteRepositorio repo;

    public IngredienteService(IngredienteRepositorio repo) {
        this.repo = repo;
    }

    @Transactional
    public IngredienteViewDTO create(CriarIngredienteDTO dto) {
        var ing = new Ingrediente();
        ing.setNome(dto.nome());
        ing.setUnidadeBase(dto.unidadeBase());
        ing.setKcalPor100(dto.kcalPor100());
        ing.setProteinaPor100(dto.proteinaPor100());
        ing.setCarboPor100(dto.carboPor100());
        ing.setGorduraPor100(dto.gorduraPor100());
        var salvo = repo.save(ing);
        return new IngredienteViewDTO(salvo);
    }

    @Transactional(readOnly = true)
    public List<IngredienteViewDTO> findAll() {
        return repo.findAll().stream()
                .map(IngredienteViewDTO::new)
                .toList();
    }
}
