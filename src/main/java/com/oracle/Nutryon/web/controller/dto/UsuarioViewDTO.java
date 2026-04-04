package com.oracle.nutryon.web.controller.dto;

import com.oracle.nutryon.domain.entity.Usuario;

public record UsuarioViewDTO(Long id, String nome, String email) {
    public UsuarioViewDTO(Usuario usuario) {
        this(usuario.getId(), usuario.getNome(), usuario.getEmail());
    }
}
