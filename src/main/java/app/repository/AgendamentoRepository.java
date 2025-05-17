package app.repository;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import app.entity.Agendamento;


public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {

	public List<Agendamento> findByDataHoraBetween(LocalDateTime startDate, LocalDateTime endDate);



	public List<Agendamento> findByClienteId(Long clienteId);

	@Query("SELECT a FROM Agendamento a JOIN a.servicos s WHERE LOWER(s.nomeServico) LIKE LOWER(CONCAT('%', :nomeServico, '%'))")
	public List<Agendamento> buscarAgendamentoPorNomeServico(@Param(value = "nomeServico") String nomeServico);
	
	public List<Agendamento> findByCliente_NomeClienteContainingIgnoreCase(String nomeCliente);
	
}

