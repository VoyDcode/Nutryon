package com.oracle.nutryon.repository;

import com.oracle.nutryon.domain.entity.Ingrediente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface IngredienteRepositorio extends JpaRepository<Ingrediente, Long> {
  Optional<Ingrediente> findByNomeIgnoreCase(String nome);
}
