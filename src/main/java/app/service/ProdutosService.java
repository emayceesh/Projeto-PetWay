package app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.entity.Cliente;
import app.entity.Produtos;
import app.repository.ProdutosRepository;

@Service
public class ProdutosService {

	@Autowired
	private ProdutosRepository produtoRepository;

	public String save(Produtos produto) {
		if (produto.getId() == null) {
			return "ID do produto não pode ser nulo!";
		}

		Produtos produtoExistente = produtoRepository.findById(produto.getId()).orElse(null);

		if (produtoExistente != null) {

			produtoExistente.setQuantidade(produtoExistente.getQuantidade() + produto.getQuantidade()); // Aqui soma a
																										// quantidade
			produtoRepository.save(produtoExistente);
			return "Produto já existente, quantidade atualizada com sucesso!";
		} else {

			produtoRepository.save(produto);
			return "Produto adicionado ao produto com sucesso!";
		}
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

	public List<Produtos> findByNome(String nome) {
		return produtoRepository.findByNomeStartingWith(nome);
	}

	public List<Produtos> findByCategoria(String categoria) {
		return produtoRepository.findByCategoria(categoria);
	}
}
