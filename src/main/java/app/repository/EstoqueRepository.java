package app.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import app.entity.Estoque;

public interface EstoqueRepository extends JpaRepository<Estoque, Long> {
    List<Estoque> findByNomeStartingWith(String nome);
}
