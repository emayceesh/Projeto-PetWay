package app.service;

import org.springframework.beans.factory.annotation.Autowired;

import app.entity.Estoque;
import app.repository.EstoqueRepository;

public class EstoqueService {

	
	 @Autowired
	    private EstoqueRepository estoqueRepository;

	    public String save(Estoque estoque) {
	        this.estoqueRepository.save(estoque);  // Save will handle both INSERT and UPDATE depending on the ID
	        return "Produto adicionado ao estoque  com sucesso";
	    }
}
