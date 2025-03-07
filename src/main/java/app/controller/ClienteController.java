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

import app.entity.Cliente;
import app.service.ClienteService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/cliente")
public class ClienteController {

	@Autowired
	private ClienteService clienteService;

	@PostMapping("/save")
	public ResponseEntity<String> save(@RequestBody @Valid Cliente cliente, BindingResult result) {

		try {
			String mensagem = this.clienteService.save(cliente);
			return new ResponseEntity<>(mensagem, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Erro ao cadastrar cliente!" + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<String> update(@RequestBody Cliente cliente, @PathVariable long id) {

		try {
			String mensagem = this.clienteService.update(cliente, id);
			return new ResponseEntity<>(mensagem, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Erro ao atualizar cliente!" + e.getMessage(), HttpStatus.BAD_REQUEST);
		}

	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> delete(@PathVariable long id){
		try {
			String mensagem = this.clienteService.delete(id);
			return new ResponseEntity<>(mensagem, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(" ", HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/findAll")
	public ResponseEntity <List<Cliente>> findAll(){

		try {
			List<Cliente> listaClientes = this.clienteService.findAll();
			return new ResponseEntity<>(listaClientes, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}

	}
	
	@GetMapping("/findById/{id}")
	public ResponseEntity<Cliente> findById(@PathVariable long id){
		try {
			Cliente cliente = this.clienteService.findById(id);
			return new ResponseEntity<>(cliente, HttpStatus.OK );
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST );

		}
	}
	
	@GetMapping("/findByCpf")
	public ResponseEntity<List<Cliente>> findByCpf(@RequestParam String cpf) {
		try {
			List<Cliente> listaCPF = this.clienteService.findByCpf(cpf);
			return new ResponseEntity<>(listaCPF, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/findByNome")
	public ResponseEntity<List<Cliente>> findByNomeClienteStartingWith(@RequestParam String nome) {
		
		try {
			List<Cliente> lista = this.clienteService.findByNomeCliente(nome);
			return new ResponseEntity<>(lista, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@GetMapping("/findByCelular")
	public ResponseEntity<List<Cliente>> findByCelularContaining(@RequestParam String celular) {
		try {
			List<Cliente> lista = this.clienteService.findByCelular(celular); 
			return new ResponseEntity<>(lista, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		
		
	}
	
}
