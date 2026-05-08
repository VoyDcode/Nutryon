package com.oracle.nutryon.service;

import com.oracle.nutryon.domain.entity.Usuario;
import com.oracle.nutryon.domain.enums.Role;
import com.oracle.nutryon.repository.UsuarioRepositorio;
import com.oracle.nutryon.web.controller.dto.CriarUsuarioDTO;
import com.oracle.nutryon.web.controller.dto.UsuarioViewDTO;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

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
    public boolean emailJaCadastrado(String email) {
        return repo.findByEmail(email).isPresent();
    }

    @Transactional
    public Optional<UsuarioViewDTO> registrar(String nome, String email, String senhaPlain, Role role) {
        if (repo.findByEmail(email).isPresent()) {
            return Optional.empty();
        }
        var u = new Usuario();
        u.setNome(nome);
        u.setEmail(email);
        u.setSenha(new BCryptPasswordEncoder().encode(senhaPlain));
        u.setRole(role != null ? role : Role.USER);
        return Optional.of(new UsuarioViewDTO(repo.save(u)));
    }

    @Transactional(readOnly = true)
    public List<UsuarioViewDTO> findAll() {
        return repo.findAll().stream()
                .map(UsuarioViewDTO::new)
                .toList();
    }
}
