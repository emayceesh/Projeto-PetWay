package app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import app.entity.Estoque;
import app.service.EstoqueService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/estoque")
public class EstoqueController {

    @Autowired
    private EstoqueService estoqueService;

    // Método para salvar ou atualizar um produto no estoque
    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody @Valid Estoque estoque) {
        try {
            // Chama o método de serviço que irá salvar ou atualizar a quantidade do produto
            String mensagem = this.estoqueService.save(estoque);
            return new ResponseEntity<>(mensagem, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao salvar produto: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Método para atualizar um produto no estoque
    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@RequestBody Estoque estoque, @PathVariable long id) {
        try {
            // Chama o método de serviço para atualizar o produto
            String mensagem = this.estoqueService.update(estoque, id);
            return new ResponseEntity<>(mensagem, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao atualizar produto: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Método para deletar um produto do estoque
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable long id) {
        try {
            // Chama o método de serviço para deletar o produto
            String mensagem = this.estoqueService.delete(id);
            return new ResponseEntity<>(mensagem, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao deletar produto: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Método para listar todos os produtos no estoque
    @GetMapping("/findAll")
    public ResponseEntity<List<Estoque>> findAll() {
        try {
            List<Estoque> lista = this.estoqueService.findAll();
            return new ResponseEntity<>(lista, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Método para buscar produtos pelo nome
    @GetMapping("/findByNome")
    public ResponseEntity<List<Estoque>> findByNome(@RequestParam String nome) {
        try {
            List<Estoque> lista = this.estoqueService.findByNome(nome);
            return new ResponseEntity<>(lista, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
