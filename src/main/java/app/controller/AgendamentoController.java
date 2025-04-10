package app.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import app.entity.Agendamento;
import app.service.AgendamentoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/agendamento")
@CrossOrigin("*")
public class AgendamentoController {

	@Autowired
	private AgendamentoService agendamentoService;

	@PostMapping("/save")
	public ResponseEntity<String> save(@RequestBody @Valid Agendamento agendamento) {
			String mensagem = agendamentoService.save(agendamento);
			return new ResponseEntity<>(mensagem, HttpStatus.CREATED);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<String> update(@RequestBody Agendamento agendamento, @PathVariable long id) {
			String mensagem = agendamentoService.update(agendamento, id);
			return new ResponseEntity<>(mensagem, HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> delete(@PathVariable long id) {
			String mensagem = agendamentoService.delete(id);
			return new ResponseEntity<>(mensagem, HttpStatus.OK);
	}

	@GetMapping("/findAll")
	public ResponseEntity<List<Agendamento>> findAll() {
			List<Agendamento> lista = agendamentoService.findAll();
			return new ResponseEntity<>(lista, HttpStatus.OK);
	}

	@GetMapping("/findById/{id}")
	public ResponseEntity<Agendamento> findById(@PathVariable long id) {
			Agendamento agendamento = agendamentoService.findById(id).orElse(null);
			if (agendamento == null) {
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<>(agendamento, HttpStatus.OK);
	}

	@GetMapping("/buscarEntreDatas")
	public ResponseEntity<List<Agendamento>> buscarAgendamentosEntreDatas(@RequestParam String startDate,
			@RequestParam String endDate) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
		    LocalDateTime start = LocalDateTime.parse(startDate, formatter);
		    LocalDateTime end = LocalDateTime.parse(endDate, formatter);
			
			List<Agendamento> agendamentos = agendamentoService.findByDataHoraBetween(start, end);
			if (agendamentos.isEmpty()) {
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<>(agendamentos, HttpStatus.OK);
	}

	@GetMapping("/buscarPorStatus")
	public ResponseEntity<List<Agendamento>> buscarAgendamentosPorStatus(@RequestParam String status) {
			List<Agendamento> agendamentos = agendamentoService.findByStatus(status);
			if (agendamentos.isEmpty()) {
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<>(agendamentos, HttpStatus.OK);
	}

	@GetMapping("/buscarPorCliente/{clienteId}")
	public ResponseEntity<List<Agendamento>> buscarAgendamentosPorCliente(@PathVariable Long clienteId) {
			List<Agendamento> agendamentos = agendamentoService.buscarPorCliente(clienteId);
			if (agendamentos.isEmpty()) {
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<>(agendamentos, HttpStatus.OK);
	}

	@GetMapping("/buscarPorServico")
	public ResponseEntity<List<Agendamento>> buscarPorNomeServico(@RequestParam String nomeServico) {
			List<Agendamento> agendamentos = agendamentoService.buscarPorNomeServico(nomeServico);
			if (agendamentos.isEmpty()) {
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<>(agendamentos, HttpStatus.OK);
	}

}
