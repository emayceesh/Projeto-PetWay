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
	public ResponseEntity<Agendamento> save(@RequestBody @Valid Agendamento agendamento) {
		try {
			Agendamento agendamentoSalvo = agendamentoService.saveRetornando(agendamento);
			return new ResponseEntity<>(agendamentoSalvo, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}




	@PutMapping("/update/{id}")
	public ResponseEntity<String> update(@RequestBody Agendamento agendamento, @PathVariable long id) {
		try {
			String mensagem = agendamentoService.update(agendamento, id);
			return new ResponseEntity<>(mensagem, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Erro ao atualizar agendamento: " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	@GetMapping("/buscarPorNomeCliente")
	public ResponseEntity<List<Agendamento>> buscarPorNomeCliente(@RequestParam String nomeCliente) {
		try {
			List<Agendamento> agendamentos = agendamentoService.buscarPorNomeCliente(nomeCliente);
			if (agendamentos.isEmpty()) {
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<>(agendamentos, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}


	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> delete(@PathVariable long id) {
		try {
			String mensagem = agendamentoService.delete(id);
			return new ResponseEntity<>(mensagem, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Erro ao deletar agendamento: " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/findAll")
	public ResponseEntity<List<Agendamento>> findAll() {
		try {
			List<Agendamento> lista = agendamentoService.findAll();
			return new ResponseEntity<>(lista, HttpStatus.OK);
		} catch (Exception e) {

			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/findById/{id}")
	public ResponseEntity<Agendamento> findById(@PathVariable long id) {
		try {
			Agendamento agendamento = agendamentoService.findById(id).orElse(null);
			if (agendamento == null) {
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<>(agendamento, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/buscarEntreDatas")
	public ResponseEntity<List<Agendamento>> buscarAgendamentosEntreDatas(@RequestParam String startDate,
			@RequestParam String endDate) {

		try {
			
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
		    LocalDateTime start = LocalDateTime.parse(startDate, formatter);
		    LocalDateTime end = LocalDateTime.parse(endDate, formatter);
			
			List<Agendamento> agendamentos = agendamentoService.findByDataHoraBetween(start, end);
			if (agendamentos.isEmpty()) {
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<>(agendamentos, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/buscarPorStatus")
	public ResponseEntity<List<Agendamento>> buscarAgendamentosPorStatus(@RequestParam String status) {
		try {
			List<Agendamento> agendamentos = agendamentoService.findByStatus(status);
			if (agendamentos.isEmpty()) {
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<>(agendamentos, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/buscarPorCliente/{clienteId}")
	public ResponseEntity<List<Agendamento>> buscarAgendamentosPorCliente(@PathVariable Long clienteId) {
		try {
			List<Agendamento> agendamentos = agendamentoService.buscarPorCliente(clienteId);
			if (agendamentos.isEmpty()) {
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<>(agendamentos, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/buscarPorServico")
	public ResponseEntity<List<Agendamento>> buscarPorNomeServico(@RequestParam String nomeServico) {
		try {
			List<Agendamento> agendamentos = agendamentoService.buscarPorNomeServico(nomeServico);
			if (agendamentos.isEmpty()) {
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<>(agendamentos, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}

}
