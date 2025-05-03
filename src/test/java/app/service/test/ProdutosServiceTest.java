package app.service.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import app.entity.Produtos;
import app.repository.ProdutosRepository;
import app.service.ProdutosService;

class ProdutosServiceTest {

    @InjectMocks
    private ProdutosService produtosService;

    @Mock
    private ProdutosRepository produtosRepository;

    private Produtos produtoValido;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        produtoValido = new Produtos();
        produtoValido.setId(1L);
        produtoValido.setNome("Sabonete");
        produtoValido.setPreco(BigDecimal.valueOf(10.0));
        produtoValido.setQuantidade(10);
        produtoValido.setCategoria("Higiene");
    }

    // 🧪 Testes Unitários (validam apenas regras internas da service)

    @Test
    void deveSalvarProdutoComDadosValidos() {
        when(produtosRepository.save(any(Produtos.class))).thenReturn(produtoValido);

        String result = produtosService.save(produtoValido);

        assertThat(result).isEqualTo("Produto salvo com sucesso!");
        verify(produtosRepository, times(1)).save(produtoValido);
    }

    @Test
    void naoDeveSalvarProdutoComPrecoZero() {
        produtoValido.setPreco(BigDecimal.ZERO);

        assertThatThrownBy(() -> produtosService.save(produtoValido))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Preço deve ser positivo");
    }

    @Test
    void naoDeveSalvarProdutoComEstoqueNegativo() {
        produtoValido.setQuantidade(-5);

        assertThatThrownBy(() -> produtosService.save(produtoValido))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Quantidade em estoque não pode ser negativa");
    }

    @Test
    void deveRetornarMensagemAoAtualizarProdutoExistente() {
        when(produtosRepository.existsById(1L)).thenReturn(true);

        String result = produtosService.update(produtoValido, 1L);

        assertThat(result).isEqualTo("Produto atualizado com sucesso!");
        verify(produtosRepository).save(produtoValido);
    }

    @Test
    void deveRetornarMensagemAoNaoEncontrarProdutoParaAtualizar() {
        when(produtosRepository.existsById(1L)).thenReturn(false);

        String result = produtosService.update(produtoValido, 1L);

        assertThat(result).isEqualTo("Produto não encontrado no produto!");
    }

    // ✅ Testes de Integração simulados com Mockito (podem ser adaptados para @SpringBootTest)

    @Test
    void deveDeletarProdutoExistente() {
        when(produtosRepository.existsById(1L)).thenReturn(true);

        String result = produtosService.delete(1L);

        assertThat(result).isEqualTo("Produto deletado com sucesso!");
        verify(produtosRepository).deleteById(1L);
    }

    @Test
    void deveRetornarMensagemAoTentarDeletarProdutoInexistente() {
        when(produtosRepository.existsById(1L)).thenReturn(false);

        String result = produtosService.delete(1L);

        assertThat(result).isEqualTo("Produto não encontrado para exclusão!");
    }

    @Test
    void deveRetornarProdutoPorId() {
        when(produtosRepository.findById(1L)).thenReturn(Optional.of(produtoValido));

        Produtos result = produtosService.findById(1L);

        assertThat(result).isEqualTo(produtoValido);
    }

    @Test
    void deveRetornarTodosProdutos() {
        List<Produtos> lista = List.of(produtoValido, produtoValido);
        when(produtosRepository.findAll()).thenReturn(lista);

        List<Produtos> result = produtosService.findAll();

        assertThat(result).hasSize(2);
    }

    @Test
    void deveBuscarPorNome() {
        when(produtosRepository.findByNomeIgnoreCaseStartingWith("Sab")).thenReturn(List.of(produtoValido));

        List<Produtos> result = produtosService.findByNomeIgnoreCaseStartingWith("Sab");

        assertThat(result).contains(produtoValido);
    }

    @Test
    void deveBuscarPorCategoria() {
        when(produtosRepository.findByCategoriaIgnoreCaseStartingWith("Hig")).thenReturn(List.of(produtoValido));

        List<Produtos> result = produtosService.findByCategoriaIgnoreCaseStartingWith("Hig");

        assertThat(result).contains(produtoValido);
    }

    @Test
    void deveBuscarPorNomeECategoria() {
        when(produtosRepository.findByNomeContainingIgnoreCaseAndCategoriaContainingIgnoreCase("Sab", "Hig"))
                .thenReturn(List.of(produtoValido));

        List<Produtos> result = produtosService.findByNomeAndCategoria("Sab", "Hig");

        assertThat(result).contains(produtoValido);
    }

    @Test
    void deveRetornarListaVaziaSeNenhumProdutoEncontradoPorNome() {
        when(produtosRepository.findByNomeIgnoreCaseStartingWith("X")).thenReturn(List.of());

        List<Produtos> result = produtosService.findByNomeIgnoreCaseStartingWith("X");

        assertThat(result).isEmpty();
    }

    @Test
    void deveRetornarListaVaziaSeNenhumProdutoEncontradoPorCategoria() {
        when(produtosRepository.findByCategoriaIgnoreCaseStartingWith("Z")).thenReturn(List.of());

        List<Produtos> result = produtosService.findByCategoriaIgnoreCaseStartingWith("Z");

        assertThat(result).isEmpty();
    }
}
