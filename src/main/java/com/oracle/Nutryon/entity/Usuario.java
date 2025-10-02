package com.oracle.Nutryon.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity @Table(name="usuarios")
public class Usuario {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Long id;

  @NotBlank private String nome;
  @Email @NotBlank @Column(unique=true) private String email;

  // getters/setters
}
