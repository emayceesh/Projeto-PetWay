package app.service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
		if (agendamento.getDataHora() != null) {
			// Formatar a hora no formato HH:mm
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
			String horaFormatada = agendamento.getDataHora().toLocalTime().format(formatter);
			agendamento.setHora(horaFormatada); // se for String
		}
		agendamentoRepository.save(agendamento);
		return "Agendamento salvo com sucesso!";
	}



	public String update(Agendamento agendamento, long id) {
		// Se hora vier nulo, extrai de dataHora e converte para String
		if ((agendamento.getHora() == null || agendamento.getHora().isEmpty()) && agendamento.getDataHora() != null) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
			String horaConvertida = agendamento.getDataHora().toLocalTime().format(formatter);
			agendamento.setHora(horaConvertida);
		}

		return agendamentoRepository.findById(id).map(existing -> {
			existing.setDataHora(agendamento.getDataHora());
			existing.setHora(agendamento.getHora());

			existing.setCliente(agendamento.getCliente());
			// outros campos que quiser atualizar...
			agendamentoRepository.save(existing);
			return "Agendamento atualizado com sucesso!";
		}).orElseThrow(() -> new RuntimeException("Agendamento não encontrado com id: " + id));
	}
	public List<Agendamento> buscarPorNomeCliente(String nomeCliente) {
		return agendamentoRepository.findByCliente_NomeClienteContainingIgnoreCase(nomeCliente);
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

	public List<Agendamento> findByDataHoraBetween(LocalDateTime startDate, LocalDateTime endDate){
		return agendamentoRepository.findByDataHoraBetween(startDate, endDate);
	}



	public List<Agendamento> buscarPorCliente(Long clienteId) {
		return agendamentoRepository.findByClienteId(clienteId);
	}

	public List<Agendamento> buscarPorNomeServico(String nomeServico){
		return agendamentoRepository.buscarAgendamentoPorNomeServico(nomeServico);
	}

	public Agendamento saveRetornando(Agendamento agendamento) {
		if (agendamento.getDataHora() != null) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
			String horaFormatada = agendamento.getDataHora().toLocalTime().format(formatter);
			agendamento.setHora(horaFormatada);
		}
		return agendamentoRepository.save(agendamento);
	}


}
	