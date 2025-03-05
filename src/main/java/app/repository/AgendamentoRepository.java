package app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import app.entity.Agendamento;
import app.entity.Animais;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
	public List<Agendamento> findByNomeCompletoStartingWith(String nome);
}
