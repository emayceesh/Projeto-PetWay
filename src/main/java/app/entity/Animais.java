package app.entity;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter 
@AllArgsConstructor
@NoArgsConstructor


public class Animais {

	@NotBlank(message = "Nome não pode ser Nulo.")
	private String nome;
	
	
	@NotBlank (message = "Sexo não pode ser nulo rs ")
	 private String sexoAnimal;
	 
	 private Boolean cadastroCompleto;
	 
}
