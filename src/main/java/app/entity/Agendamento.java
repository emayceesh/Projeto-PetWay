package app.entity;

import jakarta.persistence.Entity;
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
public class Agendamento {

    @Id
    private Long id;

    @NotBlank(message = "Data não pode ser nula.")
    private String data;

    @NotBlank(message = "Hora não pode ser nula.")
    private String hora;

    @NotBlank(message = "Cliente não pode ser nulo.")
    private String cliente;

	public void setId(long id) {
		this.id = id;
		
	}

}
