package app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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

import app.entity.Cliente;
import app.service.ClienteService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/cliente")
@CrossOrigin("*")
public class ClienteController {

	@Autowired
	private ClienteService clienteService;

	@PostMapping("/save")
	public ResponseEntity<String> save(@RequestBody @Valid Cliente cliente, BindingResult result) {
			String mensagem = this.clienteService.save(cliente);
			return new ResponseEntity<>(mensagem, HttpStatus.OK);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<String> update(@RequestBody Cliente cliente, @PathVariable long id) {
			String mensagem = this.clienteService.update(cliente, id);
			return new ResponseEntity<>(mensagem, HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> delete(@PathVariable long id){
			String mensagem = this.clienteService.delete(id);
			return new ResponseEntity<>(mensagem, HttpStatus.OK);
	}
	
	@GetMapping("/findAll")
	public ResponseEntity <List<Cliente>> findAll(){
			List<Cliente> listaClientes = this.clienteService.findAll();
			return new ResponseEntity<>(listaClientes, HttpStatus.OK);
	}
	
	@GetMapping("/findById/{id}")
	public ResponseEntity<Cliente> findById(@PathVariable long id){
			Cliente cliente = this.clienteService.findById(id);
			return new ResponseEntity<>(cliente, HttpStatus.OK );
	}
	
	@GetMapping("/findByCpf")
	public ResponseEntity<List<Cliente>> findByCpf(@RequestParam String cpf) {
			List<Cliente> listaCPF = this.clienteService.findByCpf(cpf);
			return new ResponseEntity<>(listaCPF, HttpStatus.OK);
	}
	
	@GetMapping("/findByNome")
	public ResponseEntity<List<Cliente>> findByNomeClienteIgnoreCaseStartingWith(@RequestParam String nome) {
			List<Cliente> lista = this.clienteService.findByNomeClienteIgnoreCaseStartingWith(nome);
			return new ResponseEntity<>(lista, HttpStatus.OK);
	}
	
	@GetMapping("/findByCelular")
	public ResponseEntity<List<Cliente>> findByCelularContaining(@RequestParam String celular) {
			List<Cliente> lista = this.clienteService.findByCelular(celular); 
			return new ResponseEntity<>(lista, HttpStatus.OK);
	}
	
}
