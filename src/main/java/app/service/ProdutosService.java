package app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.entity.Produtos;
import app.repository.ProdutosRepository;

@Service
public class ProdutosService {

	@Autowired
	private ProdutosRepository estoqueRepository;

	public String save(Produtos estoque) {
		if (estoque.getId() == null) {
			return "ID do produto não pode ser nulo!";
		}

		Produtos produtoExistente = estoqueRepository.findById(estoque.getId()).orElse(null);

		if (produtoExistente != null) {

			produtoExistente.setQuantidade(produtoExistente.getQuantidade() + estoque.getQuantidade()); // Aqui soma a
																										// quantidade
			estoqueRepository.save(produtoExistente);
			return "Produto já existente, quantidade atualizada com sucesso!";
		} else {

			estoqueRepository.save(estoque);
			return "Produto adicionado ao estoque com sucesso!";
		}
	}

	public String update(Produtos estoque, long id) {
		if (!estoqueRepository.existsById(id)) {
			return "Produto não encontrado no estoque!";
		}

		estoque.setId(id);
		estoqueRepository.save(estoque);
		return "Produto atualizado com sucesso!";
	}

	public String delete(long id) {
		if (!estoqueRepository.existsById(id)) {
			return "Produto não encontrado para exclusão!";
		}

		estoqueRepository.deleteById(id);
		return "Produto deletado com sucesso!";
	}

	public List<Produtos> findAll() {
		return estoqueRepository.findAll();
	}

	public List<Produtos> findByNome(String nome) {
		return estoqueRepository.findByNomeStartingWith(nome);
	}
}
