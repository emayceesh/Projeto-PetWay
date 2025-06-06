package app.entity;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Produtos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  
    private Long id;

    @NotNull(message = "O nome do produto é obrigatório")
    private String nome;

    private String descricao;
    
    @Positive(message = "O preço deve ser positivo")
    @NotNull(message = "O preço do produto é obrigatório")
    private BigDecimal preco;
    
    @Min(value = 0, message = "Quantidade em estoque não pode ser negativa")
    @NotNull(message = "A quantidade em estoque é obrigatória")
    private Integer quantidade;
    
    @NotBlank
    private String categoria;//Ração, Brinquedos, Acessórios, Medicamentos etc.

}
