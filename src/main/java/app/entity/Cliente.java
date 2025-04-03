package app.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cliente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotBlank(message = "Nome não pode estar vazio!")
	@Pattern(regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ' ]{3,100}$", message = "O nome deve conter apenas letras!")
	private String nomeCliente;
	
	
	@NotBlank(message = "CPF não pode estar vazio!")
	private String cpf;
	
	@NotNull
	@NotBlank(message = "Celular não pode estar vazio!")
	@Pattern(regexp = "\\d{10,15}", message = "O celular deve conter apenas números")
	private String celular;
	
	private String telefone;
	
	@NotNull
	@NotBlank(message = "Endereço não pode estar vazio!")
	private String endereco;
	
	private Boolean cadastroCompleto;	
	
	@OneToMany(mappedBy = "cliente")
	@JsonIgnoreProperties("cliente")
	private List<Animais> animais;
	
	@OneToMany(mappedBy = "cliente")
	@JsonIgnoreProperties("cliente")
	private List<Agendamento> agendamentos;
	
	
	
}
