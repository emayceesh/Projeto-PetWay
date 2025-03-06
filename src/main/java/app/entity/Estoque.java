package app.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Estoque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  
    private Long id;

    @NotBlank(message = "Nome não pode ser nulo")
    private String nome;

    private Long quantidade;  

   
    public Long getId() {
        return id;
    }

    
    public void setId(Long id) {
        this.id = id;
    }

   
    public String getNome() {
        return nome;
    }

 
    public void setNome(String nome) {
        this.nome = nome;
    }

    
    public Long getQuantidade() {
        return quantidade;
    }


    public void setQuantidade(Long quantidade) {
        this.quantidade = quantidade;
    }
}
