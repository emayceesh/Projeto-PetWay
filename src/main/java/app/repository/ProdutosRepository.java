package app.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import app.entity.Produtos;

public interface ProdutosRepository extends JpaRepository<Produtos, Long> {
    public List<Produtos> findByNomeIgnoreCaseStartingWith(String nome);
    
    public List<Produtos> findByCategoriaIgnoreCaseStartingWith(String categoria);
    
    //basicamente busca pelo nome e pela categoria juntos
    public List<Produtos> findByNomeContainingIgnoreCaseAndCategoriaContainingIgnoreCase(String nome, String categoria);

}
