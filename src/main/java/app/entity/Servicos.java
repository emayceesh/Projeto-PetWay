package app.entity;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Servicos {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "O nome do serviço é obrigatório")
	private String nomeServico;
	
	private String descricao;
	
	@NotNull(message = "O preço do serviço é obrigatório")
	private BigDecimal preco;//li por ai que BigDecimal é melhor pra quando for lidar com dinheiro
	
	@NotNull(message = "A duração do serviço é obrigatória")
	private Integer duracao;//duração em minutos
	
	private Boolean disponivel;
	
	@ManyToMany(mappedBy = "servicos")
	@JsonIgnoreProperties("servicos")
	private List<Agendamento> agendamentos;


}
