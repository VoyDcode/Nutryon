package com.oracle.nutryon.domain.entity;

<<<<<<< HEAD
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Entity
@Table(name = "itens_refeicao")
public class ItemRefeicao {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(optional = false)
  @JoinColumn(name = "refeicao_id")
  private Refeicao refeicao;

  @ManyToOne(optional = false)
  @JoinColumn(name = "ingrediente_id")
  private Ingrediente ingrediente;

  @NotNull @DecimalMin("0.01")
  private BigDecimal quantidade; // g ou ml conforme ingrediente

  // getters/setters
  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }
  public Refeicao getRefeicao() { return refeicao; }
  public void setRefeicao(Refeicao refeicao) { this.refeicao = refeicao; }
  public Ingrediente getIngrediente() { return ingrediente; }
  public void setIngrediente(Ingrediente ingrediente) { this.ingrediente = ingrediente; }
  public BigDecimal getQuantidade() { return quantidade; }
  public void setQuantidade(BigDecimal quantidade) { this.quantidade = quantidade; }
=======
public class ItemRefeicao {
    
>>>>>>> 9147ed54a9dee00ea1a3b023c3228242dd7b0912
}
