package app.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.entity.Agendamento;
import app.repository.AgendamentoRepository;

@Service
public class AgendamentoService {

	@Autowired
	private AgendamentoRepository agendamentoRepository;

	public String save(Agendamento agendamento) {
		agendamentoRepository.save(agendamento);
		return "Agendamento salvo com sucesso!";
	}

	public String update(Agendamento agendamento, long id) {
		if (!agendamentoRepository.existsById(id)) {
			return "Agendamento não encontrado para atualização.";
		}

		agendamento.setId(id);
		agendamentoRepository.save(agendamento);
		return "Agendamento atualizado com sucesso!";
	}

	public String delete(long id) {
		if (!agendamentoRepository.existsById(id)) {
			return "Agendamento não encontrado para exclusão.";
		}
		agendamentoRepository.deleteById(id);
		return "Agendamento deletado com sucesso!";
	}

	public List<Agendamento> findAll() {
		return agendamentoRepository.findAll();
	}

	public Optional<Agendamento> findById(long id) {
		return agendamentoRepository.findById(id);
	}
	
	public List<Agendamento> findByDataBetween(LocalDateTime startDate, LocalDateTime endDate){
		return agendamentoRepository.findByDataHoraBetween(startDate, endDate);
	}
	
	public List<Agendamento> findByStatus(String status){
		return agendamentoRepository.findByStatus(status);
	}
	
	public List<Agendamento> buscarPorCliente(Long clienteId) {
        return agendamentoRepository.findByClienteId(clienteId);
    }
}
