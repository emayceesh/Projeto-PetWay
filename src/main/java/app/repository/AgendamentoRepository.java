package app.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import app.entity.Agendamento;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
	
	 public List<Agendamento> findByDataHoraBetween(LocalDateTime startDate, LocalDateTime endDate);
	 
	 public List<Agendamento> findByStatus(String status);
	 
	 public List<Agendamento> findByClienteId(Long clienteId);

}
