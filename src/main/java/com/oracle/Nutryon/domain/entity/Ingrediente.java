package com.oracle.nutryon.domain.entity;

import com.oracle.nutryon.domain.enums.UnidadeBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Entity
@Table(name = "ingredientes")
public class Ingrediente {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  @Column(unique = true)
  private String nome;

  @NotNull
  @Enumerated(EnumType.STRING)
  private UnidadeBase unidadeBase;

  @NotNull @DecimalMin("0") private BigDecimal kcalPor100;
  @NotNull @DecimalMin("0") private BigDecimal proteinaPor100;
  @NotNull @DecimalMin("0") private BigDecimal carboPor100;
  @NotNull @DecimalMin("0") private BigDecimal gorduraPor100;

  // getters/setters
  public Long getId() { return id; }
  public void setId(Long id) { 
    this.id = id; }
  public String getNome() { 
    return nome; }

  public void setNome(String nome) { 
    this.nome = nome; }

  public UnidadeBase getUnidadeBase() { 
    return unidadeBase; }
    
  public void setUnidadeBase(UnidadeBase unidadeBase) { 
    this.unidadeBase = unidadeBase; }

  public BigDecimal getKcalPor100() {
     return kcalPor100; }

  public void setKcalPor100(BigDecimal v) { 
    this.kcalPor100 = v; }

  public BigDecimal getProteinaPor100() {
    return proteinaPor100; }

  public void setProteinaPor100(BigDecimal v) {
     this.proteinaPor100 = v; }

  public BigDecimal getCarboPor100() { 
    return carboPor100; }

  public void setCarboPor100(BigDecimal v) { 
    this.carboPor100 = v; }

  public BigDecimal getGorduraPor100() { 
    return gorduraPor100; }

  public void setGorduraPor100(BigDecimal v) { 
    this.gorduraPor100 = v; }

}
