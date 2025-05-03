package app.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.entity.Produtos;
import app.repository.ProdutosRepository;

@Service
public class ProdutosService {

	@Autowired
	private ProdutosRepository produtoRepository;

	public String save(Produtos produto) {
	    if (produto.getQuantidade() < 0) {
	        throw new IllegalArgumentException("Quantidade em estoque não pode ser negativa");
	    }
	    if (produto.getPreco().compareTo(BigDecimal.ZERO) <= 0) {
	        throw new IllegalArgumentException("Preço deve ser positivo");
	    }
	    
	    produtoRepository.save(produto);
	    return "Produto salvo com sucesso!";
	}

	public String update(Produtos produto, long id) {
		if (!produtoRepository.existsById(id)) {
			return "Produto não encontrado no produto!";
		}

		produto.setId(id);
		produtoRepository.save(produto);
		return "Produto atualizado com sucesso!";
	}

	public String delete(long id) {
		if (!produtoRepository.existsById(id)) {
			return "Produto não encontrado para exclusão!";
		}

		produtoRepository.deleteById(id);
		return "Produto deletado com sucesso!";
	}
	
	public Produtos findById(long id) {

		Produtos produto = this.produtoRepository.findById(id).get();

		return produto;
	}

	public List<Produtos> findAll() {
		return produtoRepository.findAll();
	}

	public List<Produtos> findByNomeIgnoreCaseStartingWith(String nome) {
		return produtoRepository.findByNomeIgnoreCaseStartingWith(nome);
	}

	public List<Produtos> findByCategoriaIgnoreCaseStartingWith(String categoria) {
		return produtoRepository.findByCategoriaIgnoreCaseStartingWith(categoria);
	}
	
	public List<Produtos> findByNomeAndCategoria(String nome, String categoria) {
	    return this.produtoRepository.findByNomeContainingIgnoreCaseAndCategoriaContainingIgnoreCase(nome, categoria);
	}

}
