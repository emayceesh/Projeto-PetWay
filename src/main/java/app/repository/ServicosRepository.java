package app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import app.entity.Servicos;

public interface ServicosRepository extends JpaRepository<Servicos, Long>{

	public List<Servicos> findByNomeServicoIgnoreCaseStartingWith(String nome);
	
	public List<Servicos> findByDisponivelIgnoreCaseStartingWith (Boolean disponivel);
	
}
