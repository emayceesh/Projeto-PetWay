package app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.entity.Animais;
import app.service.AnimaisService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/animal")
public class AnimaisController {

	@Autowired
	private AnimaisService animaisService;

	@PostMapping("/save")
	public ResponseEntity<String> save (@RequestBody @Valid Animais animais, BindingResult result){

		try {
			String mensagem = this.animaisService.save(animais);
			return new ResponseEntity<>(mensagem, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Deu erro!" +e.getMessage(), HttpStatus.BAD_REQUEST);
		}	
	}
}
