package app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import app.entity.Animais;

public interface AnimaisRepository extends JpaRepository<Animais, Long> {

	public List<Animais> findByNomeAnimalIgnoreCaseStartingWith(String nomeAnimal);
	
	public List<Animais> findByRacaIgnoreCaseContaining(String raca);
	
	public List<Animais> findByNomeAnimalStartingWithIgnoreCaseAndRacaIgnoreCase(String nomeAnimal, String raca);

	
}
