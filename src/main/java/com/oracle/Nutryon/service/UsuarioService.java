package com.oracle.nutryon.service;

import com.oracle.nutryon.domain.entity.Usuario;
import com.oracle.nutryon.repository.UsuarioRepositorio;
import com.oracle.nutryon.web.controller.dto.CriarUsuarioDTO;
import com.oracle.nutryon.web.controller.dto.UsuarioViewDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepositorio repo;

    public UsuarioService(UsuarioRepositorio repo) {
        this.repo = repo;
    }

    @Transactional
    public UsuarioViewDTO create(CriarUsuarioDTO dto) {
        var u = new Usuario();
        u.setNome(dto.nome());
        u.setEmail(dto.email());
        var salvo = repo.save(u);
        return new UsuarioViewDTO(salvo);
    }

    @Transactional(readOnly = true)
    public List<UsuarioViewDTO> findAll() {
        return repo.findAll().stream()
                .map(UsuarioViewDTO::new)
                .toList();
    }
}
