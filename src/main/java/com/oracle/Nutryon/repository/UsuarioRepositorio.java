package com.oracle.nutryon.repository;

import com.oracle.nutryon.domain.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepositorio extends JpaRepository <Usuario, Long> {
    
}
