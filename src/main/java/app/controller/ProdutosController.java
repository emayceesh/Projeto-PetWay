package app.controller;

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

import app.entity.Produtos;
import app.service.ProdutosService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/produtos")
@CrossOrigin("*")
public class ProdutosController {

	@Autowired
	private ProdutosService estoqueService;

	@PostMapping("/save")
	public ResponseEntity<String> save(@RequestBody @Valid Produtos estoque) {
			String mensagem = this.estoqueService.save(estoque);
			return new ResponseEntity<>(mensagem, HttpStatus.OK);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<String> update(@RequestBody Produtos estoque, @PathVariable long id) {
			String mensagem = this.estoqueService.update(estoque, id);
			return new ResponseEntity<>(mensagem, HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> delete(@PathVariable long id) {
			String mensagem = this.estoqueService.delete(id);
			return new ResponseEntity<>(mensagem, HttpStatus.OK);
	}

	@GetMapping("/findById/{id}")
	public ResponseEntity<Produtos> findById(@PathVariable long id){
			Produtos produto = this.estoqueService.findById(id);
			return new ResponseEntity<>(produto, HttpStatus.OK );
	}
	
	@GetMapping("/findAll")
	public ResponseEntity<List<Produtos>> findAll() {
			List<Produtos> lista = this.estoqueService.findAll();
			return new ResponseEntity<>(lista, HttpStatus.OK);
	}

	@GetMapping("/findByNome")
	public ResponseEntity<List<Produtos>> findByNome(@RequestParam String nome) {
			List<Produtos> lista = this.estoqueService.findByNomeIgnoreCaseStartingWith(nome);
			return new ResponseEntity<>(lista, HttpStatus.OK);
	}

	@GetMapping("/findByCategoria")
	public ResponseEntity<List<Produtos>> findByCategoria(@RequestParam String categoria) {
			List<Produtos> lista = this.estoqueService.findByCategoriaIgnoreCaseStartingWith(categoria);
			return new ResponseEntity<>(lista, HttpStatus.OK);
	}
	
	@GetMapping("/findByNomeAndCategoria")
	public ResponseEntity<List<Produtos>> findByNomeAndCategoria(@RequestParam String nome, @RequestParam String categoria) {
	    List<Produtos> lista = this.estoqueService.findByNomeAndCategoria(nome, categoria);
	    return new ResponseEntity<>(lista, HttpStatus.OK);
	}

}
