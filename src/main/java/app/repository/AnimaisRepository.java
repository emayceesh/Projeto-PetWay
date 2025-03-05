package app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import app.entity.Animais;

public interface AnimaisRepository extends JpaRepository<Animais, Long> {

	
	
}
