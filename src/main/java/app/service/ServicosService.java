package app.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.entity.Servicos;
import app.repository.ServicosRepository;

@Service
public class ServicosService {

	@Autowired
	private ServicosRepository servicosRepository;

	public String save(Servicos servicos) {
	    
	    if (servicos == null) {
	        throw new NullPointerException("Serviço não pode ser nulo");
	    }
	    
	    if (servicos.getNomeServico() == null || servicos.getNomeServico().trim().isEmpty()) {
	        throw new IllegalArgumentException("Nome do serviço é obrigatório");
	    }
	    if (servicos.getPreco() == null || servicos.getPreco().compareTo(BigDecimal.ZERO) <= 0) {
	        throw new IllegalArgumentException("Valor deve ser positivo");
	    }
	    
	    this.servicosRepository.save(servicos);
	    return "Serviço cadastrado com sucesso!";
	}

	public String update(Servicos servicos, long id) {

		servicos.setId(id);
		this.servicosRepository.save(servicos);

		return "Serviço alterado com sucesso!";
	}

	public String delete(long id) {

		this.servicosRepository.deleteById(id);

		return "Serviço deletado com sucesso!";
	}

	public List<Servicos> findAll() {

		List<Servicos> listaServicoss = this.servicosRepository.findAll();

		return listaServicoss;
	}

	public Servicos findById(long id) {

		Servicos servicos = this.servicosRepository.findById(id).get();

		return servicos;
	}

	public List<Servicos> findByNomeServicosIgnoreCaseStartingWith(String nome) {

		return this.servicosRepository.findByNomeServicoIgnoreCaseStartingWith(nome);
	}

	public List<Servicos> findByDisponivel(Boolean disponivel) {
		return this.servicosRepository.findByDisponivel(disponivel);
	}

}
