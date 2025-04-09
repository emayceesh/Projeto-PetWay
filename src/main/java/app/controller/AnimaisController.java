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
import app.entity.Cliente;
import app.service.AnimaisService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/animal")
@CrossOrigin("*")
public class AnimaisController {

    @Autowired
    private AnimaisService animaisService;

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody @Valid Animais animais) {
        try {
            String mensagem = this.animaisService.save(animais);
            return new ResponseEntity<>(mensagem, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao salvar animal: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@RequestBody Animais animais, @PathVariable long id) {
        try {
            String mensagem = this.animaisService.update(animais, id);
            return new ResponseEntity<>(mensagem, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao atualizar animal: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable long id) {
        try {
            String mensagem = this.animaisService.delete(id);
            return new ResponseEntity<>(mensagem, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao deletar animal: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    
    @GetMapping("/findById/{id}")
	public ResponseEntity<Animais> findById(@PathVariable long id){
		try {
			Animais animais = this.animaisService.findById(id);
			return new ResponseEntity<>(animais, HttpStatus.OK );
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST );

		}
	}

    @GetMapping("/findByNome")
    public ResponseEntity<List<Animais>> findByNomeAnimal(@RequestParam String nome) {
        try {
            List<Animais> lista = this.animaisService.findByNomeAnimalIgnoreCaseStartingWith(nome);
            return new ResponseEntity<>(lista, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/findAll")
	public ResponseEntity <List<Animais>> findAll(){
    	
		try {
			List<Animais> listaAnimais = this.animaisService.findAll();
			return new ResponseEntity<>(listaAnimais, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity <>(null, HttpStatus.BAD_REQUEST);
		}

	}
    
    @GetMapping("/buscarPorRaca")
    public ResponseEntity<List<Animais>> findByRacaIgnoreCaseContaining(@RequestParam String raca){
    	try {
			List<Animais> lista = this.animaisService.findByRacaIgnoreCaseContaining(raca);
			return new ResponseEntity<>(lista, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
			
		}
    }
}
