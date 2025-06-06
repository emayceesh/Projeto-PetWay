package app.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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

import app.entity.Servicos;
import app.service.ServicosService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/servicos")
@CrossOrigin("*")
public class ServicosController {

	@Autowired
	private ServicosService servicosService;

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/save")
	public ResponseEntity<String> save(@RequestBody @Valid Servicos servicos, BindingResult result) {
	    if (result.hasErrors()) {

	        String errorMessage = result.getFieldErrors().stream()
	            .map(FieldError::getDefaultMessage)
	            .collect(Collectors.joining(", "));
	        
	        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
	    }
	    
	    String mensagem = this.servicosService.save(servicos);
	    return new ResponseEntity<>(mensagem, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping("/update/{id}")
	public ResponseEntity<String> update(@RequestBody Servicos servicos, @PathVariable long id) {
			String mensagem = this.servicosService.update(servicos, id);
			return new ResponseEntity<>(mensagem, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> delete(@PathVariable long id){
			String mensagem = this.servicosService.delete(id);
			return new ResponseEntity<>(mensagem, HttpStatus.OK);
	}
	
	@GetMapping("/findAll")
	public ResponseEntity <List<Servicos>> findAll(){
			List<Servicos> listaServicoss = this.servicosService.findAll();
			return new ResponseEntity<>(listaServicoss, HttpStatus.OK);
	}
	
	@GetMapping("/findById/{id}")
	public ResponseEntity<Servicos> findById(@PathVariable long id) {
	    Servicos servicos = this.servicosService.findById(id);
	    if (servicos == null) {
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
	    }
	    return new ResponseEntity<>(servicos, HttpStatus.OK); 
	}
	
	@GetMapping("/findByNome")
	public ResponseEntity<List<Servicos>> findByNomeServicosIgnoreCaseStartingWith(@RequestParam String nome) {
			List<Servicos> lista = this.servicosService.findByNomeServicosIgnoreCaseStartingWith(nome);
			return new ResponseEntity<>(lista, HttpStatus.OK);
	}
	
	@GetMapping("/findByDisponibilidade")
	public ResponseEntity<List<Servicos>> findByDisponivel(@RequestParam Boolean disponivel) {
			List<Servicos> lista = this.servicosService.findByDisponivel(disponivel); 
			return new ResponseEntity<>(lista, HttpStatus.OK);		
	}
	
}
