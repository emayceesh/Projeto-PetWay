package app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.entity.Animais;
import app.repository.AnimaisRepository;

@Service
public class AnimaisService {
	@Autowired
	private AnimaisRepository animaisRepository;

	public String save(Animais animais) {

		this.animaisRepository.save(animais);
		return "Animal salvo com sucesso";
	}
}
