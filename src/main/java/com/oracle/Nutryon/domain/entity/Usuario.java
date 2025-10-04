package com.oracle.nutryon.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

<<<<<<< HEAD
@Entity
@Table(name = "usuarios")
public class Usuario {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  private String nome;

  @Email @NotBlank
  @Column(unique = true)
  private String email;

  
  public Long getId() { 
    return id; }

  public void setId(Long id) { 
    this.id = id; }

  public String getNome() { 
    return nome; }

  public void setNome(String nome) { 
    this.nome = nome; }

  public String getEmail() { 
    return email; }

  public void setEmail(String email) { 
    this.email = email; }
=======
@Entity @Table(name = "usuarios")
public class Usuario {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank private String nome;
  @Email @NotBlank @Column(unique = true) private String email;

  // getters/setters
>>>>>>> 9147ed54a9dee00ea1a3b023c3228242dd7b0912
}
