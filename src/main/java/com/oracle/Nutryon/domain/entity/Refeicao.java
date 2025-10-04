package com.oracle.nutryon.domain.entity;

import com.oracle.nutryon.domain.enums.TipoRefeicao;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
    
  name = "refeicoes",
  uniqueConstraints = @UniqueConstraint(columnNames = {"usuario_id","data","tipo"})
)
public class Refeicao {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(optional = false)
  @JoinColumn(name = "usuario_id")
  private Usuario usuario;

  @NotNull
  private LocalDate data;

  @NotNull
  @Enumerated(EnumType.STRING)
  private TipoRefeicao tipo;

  @OneToMany(mappedBy = "refeicao", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<ItemRefeicao> itens = new ArrayList<>();

  // getters/setters
  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }
  public Usuario getUsuario() { return usuario; }
  public void setUsuario(Usuario usuario) { this.usuario = usuario; }
  public LocalDate getData() { return data; }
  public void setData(LocalDate data) { this.data = data; }
  public TipoRefeicao getTipo() { return tipo; }
  public void setTipo(TipoRefeicao tipo) { this.tipo = tipo; }
  public List<ItemRefeicao> getItens() { return itens; }
  public void setItens(List<ItemRefeicao> itens) { this.itens = itens; }
}
