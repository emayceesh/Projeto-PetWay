package app.entity;

<<<<<<< HEAD
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class Animais {

	@ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
	
=======
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
	 
>>>>>>> 1b2afe795c4580036ca129246367f79a4db1c6e7
}
