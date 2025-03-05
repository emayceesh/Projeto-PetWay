package app.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
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
   

    @NotBlank(message = "Nome não pode ser Nulo.")
    private String nome;

    @NotBlank(message = "Sexo não pode ser nulo rs ")
    private String sexoAnimal;

    private Boolean cadastroCompleto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
	
}
