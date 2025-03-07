package app.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotNull
    @NotBlank(message = "Data não pode esar vazia.")
    private String data;
    
    @NotNull
    @NotBlank(message = "Hora não pode estar vazia.")
    private String hora;
    
    private Boolean buscarEntregar = false;
    
    private String observacao;

    @ManyToOne
    @JoinColumn(name = "fk_cliente_id")
    private Cliente cliente;
    
    @ManyToMany(mappedBy = "agendamento")
    @JsonIgnoreProperties("agendamento")
    private List<Animais> animais;

	public void setId(long id) {
		this.id = id;
		
	}

}
