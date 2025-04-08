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

import app.entity.Animais;
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
		try {
			String mensagem = this.estoqueService.save(estoque);
			return new ResponseEntity<>(mensagem, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Erro ao salvar produto: " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<String> update(@RequestBody Produtos estoque, @PathVariable long id) {
		try {
			String mensagem = this.estoqueService.update(estoque, id);
			return new ResponseEntity<>(mensagem, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Erro ao atualizar produto: " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> delete(@PathVariable long id) {
		try {
			String mensagem = this.estoqueService.delete(id);
			return new ResponseEntity<>(mensagem, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Erro ao deletar produto: " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/findById/{id}")
	public ResponseEntity<Produtos> findById(@PathVariable long id){
		try {
			Produtos produto = this.estoqueService.findById(id);
			return new ResponseEntity<>(produto, HttpStatus.OK );
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST );

		}
	}
	
	@GetMapping("/findAll")
	public ResponseEntity<List<Produtos>> findAll() {
		try {
			List<Produtos> lista = this.estoqueService.findAll();
			return new ResponseEntity<>(lista, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/findByNome")
	public ResponseEntity<List<Produtos>> findByNome(@RequestParam String nome) {
		try {
			List<Produtos> lista = this.estoqueService.findByNome(nome);
			return new ResponseEntity<>(lista, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/findByCategoria")
	public ResponseEntity<List<Produtos>> findByCategoria(@RequestParam String categoria) {
		try {
			List<Produtos> lista = this.estoqueService.findByCategoria(categoria);
			return new ResponseEntity<>(lista, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}
}
