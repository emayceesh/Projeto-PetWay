package app.controller.teest;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import app.entity.Animais;
import app.service.AnimaisService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/animal")
@CrossOrigin("*")
@Validated
public class AnimaisController {

    @Autowired
    private AnimaisService animaisService;

    @PostMapping("/save")
    public ResponseEntity<?> save(
        @Valid @RequestBody Animais animais,
        BindingResult result
    ) {
        // Validação manual
        if (result.hasErrors()) {
            return criarRespostaErroValidacao(result);
        }
        
        String mensagem = animaisService.save(animais);
        return new ResponseEntity<>(mensagem, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(
        @Valid @RequestBody Animais animais,
        BindingResult result,
        @PathVariable long id
    ) {
        // Validação manual
        if (result.hasErrors()) {
            return criarRespostaErroValidacao(result);
        }
        
        String mensagem = animaisService.update(animais, id);
        return new ResponseEntity<>(mensagem, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable long id) {
        String mensagem = animaisService.delete(id);
        return new ResponseEntity<>(mensagem, HttpStatus.OK);
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<Animais> findById(@PathVariable long id) {
        Animais animais = animaisService.findById(id);
        return new ResponseEntity<>(animais, HttpStatus.OK);
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<Animais>> findAll() {
        List<Animais> listaAnimais = animaisService.findAll();
        return new ResponseEntity<>(listaAnimais, HttpStatus.OK);
    }

    @GetMapping("/findByNome")
    public ResponseEntity<List<Animais>> findByNomeAnimal(@RequestParam String nomeAnimal) {
        List<Animais> lista = animaisService.findByNomeAnimalIgnoreCaseStartingWith(nomeAnimal);
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/findByRaca")
    public ResponseEntity<List<Animais>> findByRacaIgnoreCaseContaining(@RequestParam String raca) {
        List<Animais> lista = animaisService.findByRacaIgnoreCaseContaining(raca);
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/findByNomeAnimalAndRaca")
    public ResponseEntity<List<Animais>> findByNomeAnimalAndRaca(
        @RequestParam String nomeAnimal,
        @RequestParam String raca
    ) {
        List<Animais> lista = animaisService.findByNomeAnimalAndRacaIgnoreCase(nomeAnimal, raca);
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    // Método auxiliar para tratamento de erros de validação
    private ResponseEntity<String> criarRespostaErroValidacao(BindingResult result) {
        String mensagemErro = result.getFieldErrors().stream()
            .map(FieldError::getDefaultMessage)
            .collect(Collectors.joining("\n"));
        
        return new ResponseEntity<>(mensagemErro, HttpStatus.BAD_REQUEST);
    }
}