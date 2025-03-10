package app.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Agendamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Data e hora não podem estar vazias!")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dataHora;
    
    @NotBlank(message = "Hora não pode estar vazia.")
    private String hora;
    
    private String status;//agendado, cancelado, concluido
    
    private Boolean buscarEntregar = false;//começa em false já, caso seja true, tera busca e entrega do pet
    
    @Size(max = 100, message = "Observação deve ter no máximo 100 caracteres.")
    private String observacoes;

    @ManyToOne
    @JoinColumn(name = "fk_cliente_id")
    @JsonIgnoreProperties({"agendamentos", "animais"})
    private Cliente cliente;
    
    @ManyToMany
    @JoinTable(
        name = "agendamento_animal",
        joinColumns = @JoinColumn(name = "agendamento_id"),
        inverseJoinColumns = @JoinColumn(name = "animal_id")
    )
    @JsonIgnoreProperties({"agendamentos"})
    private List<Animais> animais;

    
    @ManyToMany
	@JoinTable(
	    name = "agendamento_servico",
	    joinColumns = @JoinColumn(name = "agendamento_id"),
	    inverseJoinColumns = @JoinColumn(name = "servico_id")
	)
	@JsonIgnoreProperties("agendamentos")
	private List<Servicos> servicos;

	public void setId(long id) {
		this.id = id;
		
	}

}
