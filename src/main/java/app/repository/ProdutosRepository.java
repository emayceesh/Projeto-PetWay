package app.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import app.entity.Produtos;

public interface ProdutosRepository extends JpaRepository<Produtos, Long> {
    public List<Produtos> findByNomeStartingWith(String nome);
    
    public List<Produtos> findByCategoria(String categoria);
}
