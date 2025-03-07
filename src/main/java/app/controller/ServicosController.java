package app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import app.entity.Servicos;
import app.service.ServicosService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/servicos")
public class ServicosController {

	@Autowired
	private ServicosService servicosService;

	@PostMapping("/save")
	public ResponseEntity<String> save(@RequestBody @Valid Servicos servicos, BindingResult result) {

		try {
			String mensagem = this.servicosService.save(servicos);
			return new ResponseEntity<>(mensagem, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Erro ao cadastrar servicos!" + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<String> update(@RequestBody Servicos servicos, @PathVariable long id) {

		try {
			String mensagem = this.servicosService.update(servicos, id);
			return new ResponseEntity<>(mensagem, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Erro ao atualizar servicos!" + e.getMessage(), HttpStatus.BAD_REQUEST);
		}

	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> delete(@PathVariable long id){
		try {
			String mensagem = this.servicosService.delete(id);
			return new ResponseEntity<>(mensagem, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(" ", HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/findAll")
	public ResponseEntity <List<Servicos>> findAll(){

		try {
			List<Servicos> listaServicoss = this.servicosService.findAll();
			return new ResponseEntity<>(listaServicoss, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}

	}
	
	@GetMapping("/findById/{id}")
	public ResponseEntity<Servicos> findById(@PathVariable long id){
		try {
			Servicos servicos = this.servicosService.findById(id);
			return new ResponseEntity<>(servicos, HttpStatus.OK );
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST );

		}
	}
	
	@GetMapping("/findByNome")
	public ResponseEntity<List<Servicos>> findByNomeServicosIgnoreCaseStartingWith(@RequestParam String nome) {
		
		try {
			List<Servicos> lista = this.servicosService.findByNomeServicosIgnoreCaseStartingWith(nome);
			return new ResponseEntity<>(lista, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@GetMapping("/findByDisponibilidade")
	public ResponseEntity<List<Servicos>> findByDisponivelIgnoreCaseStartingWith(@RequestParam Boolean disponivel) {
		try {
			List<Servicos> lista = this.servicosService.findByDisponivelIgnoreCaseStartingWith(disponivel); 
			return new ResponseEntity<>(lista, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		
		
	}
	
}
