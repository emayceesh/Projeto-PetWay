package app.entity;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class Animais {

	@ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
	
}
