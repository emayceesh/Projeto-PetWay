package app.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity  
public class Animais {

    @Id  
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
   
    @NotNull
    @NotBlank(message = "Nome não pode estar vazio!")
    @Pattern(regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ' ]{3,100}$", message = "O nome deve conter apenas letras!")
    private String nomeAnimal;

    @NotBlank(message = "Raça não pode estar vazio.")
    private String raca;
    
    @NotNull
    @NotBlank(message = "Sexo não pode estar vazio!")
    private String sexoAnimal;

    @NotNull
    @NotBlank(message = "Porte do animal não pode estar vazio!")
    private String porte;//pequeno, medio, grande
    
    private String cor;

    @ManyToOne
    @JoinColumn(name = "fk_cliente_id")
    private Cliente cliente;
    
    @ManyToMany(mappedBy = "animais")
    @JsonIgnoreProperties("animais")
    private List<Agendamento> agendamentos;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
	
}
