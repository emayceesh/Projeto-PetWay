package app.controller.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import app.controller.ProdutosController;
import app.entity.Produtos;
import app.service.ProdutosService;

@ExtendWith(MockitoExtension.class)
public class ProdutosControllerTest {

    @Mock
    private ProdutosService produtosService;

    @InjectMocks
    private ProdutosController produtosController;

    private Produtos produto;

    @BeforeEach
    void setUp() {
        produto = new Produtos();
        produto.setId(1L);
        produto.setNome("Notebook Dell");
        produto.setCategoria("Eletrônicos");
        produto.setPreco(new BigDecimal("4500.00"));
        produto.setQuantidade(10);
    }

    //TESTES DE INTEGRAÇÃO
    
    @Test
    @DisplayName("INTEGRAÇÃO - Deve salvar produto com sucesso")
    void testSaveProdutoValido() {
        // Arrange
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);
        when(produtosService.save(any(Produtos.class))).thenReturn("Produto salvo com sucesso");

        // Act
        ResponseEntity<?> response = produtosController.save(produto, bindingResult);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Produto salvo com sucesso", response.getBody());
        verify(produtosService, times(1)).save(produto);
        verify(bindingResult, times(1)).hasErrors();
    }

    @Test
    @DisplayName("INTEGRAÇÃO - Deve retornar erro ao salvar produto inválido")
    void testSaveProdutoInvalido() {
        // Arrange
        Produtos produtoInvalido = new Produtos(); // Sem nome/categoria
        BindingResult bindingResult = mock(BindingResult.class);
        
        when(bindingResult.hasErrors()).thenReturn(true);
        when(bindingResult.getFieldErrors()).thenReturn(
            List.of(new FieldError("produto", "nome", "Nome é obrigatório"))
        );
        
        // Act
        ResponseEntity<?> response = produtosController.save(produtoInvalido, bindingResult);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("Nome é obrigatório"));
        verify(bindingResult, times(1)).hasErrors();
        verify(produtosService, never()).save(any()); // Verifica que o service NÃO foi chamado
    }

    @Test
    @DisplayName("INTEGRAÇÃO - Deve atualizar produto com sucesso")
    void testUpdateProduto() {
        when(produtosService.update(any(Produtos.class), eq(1L))).thenReturn("Produto atualizado");

        ResponseEntity<String> response = produtosController.update(produto, 1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Produto atualizado", response.getBody());
    }

    @Test
    @DisplayName("INTEGRAÇÃO - Deve deletar produto com sucesso")
    void testDeleteProduto() {
        when(produtosService.delete(1L)).thenReturn("Produto deletado");

        ResponseEntity<String> response = produtosController.delete(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Produto deletado", response.getBody());
    }

    @Test
    @DisplayName("INTEGRAÇÃO - Deve retornar produto por ID")
    void testFindById() {
        when(produtosService.findById(1L)).thenReturn(produto);

        ResponseEntity<Produtos> response = produtosController.findById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Notebook Dell", response.getBody().getNome());
    }

    @Test
    @DisplayName("INTEGRAÇÃO - Deve retornar NOT_FOUND para produto inexistente")
    void testFindByIdInexistente() {
        when(produtosService.findById(99L)).thenReturn(null);

        ResponseEntity<Produtos> response = produtosController.findById(99L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    @DisplayName("INTEGRAÇÃO - Deve listar todos os produtos")
    void testFindAll() {
        when(produtosService.findAll()).thenReturn(Arrays.asList(produto));

        ResponseEntity<List<Produtos>> response = produtosController.findAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    @DisplayName("INTEGRAÇÃO - Deve buscar produtos por nome")
    void testFindByNome() {
        when(produtosService.findByNomeIgnoreCaseStartingWith("Note"))
            .thenReturn(Arrays.asList(produto));

        ResponseEntity<List<Produtos>> response = produtosController.findByNome("Note");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Notebook Dell", response.getBody().get(0).getNome());
    }

    @Test
    @DisplayName("INTEGRAÇÃO - Deve buscar produtos por categoria")
    void testFindByCategoria() {
        when(produtosService.findByCategoriaIgnoreCaseStartingWith("Elet"))
            .thenReturn(Arrays.asList(produto));

        ResponseEntity<List<Produtos>> response = produtosController.findByCategoria("Elet");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Eletrônicos", response.getBody().get(0).getCategoria());
    }

    @Test
    @DisplayName("INTEGRAÇÃO - Deve buscar por nome e categoria")
    void testFindByNomeAndCategoria() {
        when(produtosService.findByNomeAndCategoria("Note", "Elet"))
            .thenReturn(Arrays.asList(produto));

        ResponseEntity<List<Produtos>> response = produtosController
            .findByNomeAndCategoria("Note", "Elet");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }
    
    //TESTES UNITÁRIOS
    
    @Test
    @DisplayName("UNITÁRIO - Deve rejeitar preço negativo")
    void testSaveProdutoPrecoNegativo() {
        produto.setPreco(new BigDecimal("-100.00"));
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);
        when(produtosService.save(any(Produtos.class)))
            .thenThrow(new IllegalArgumentException("Preço deve ser positivo"));

        ResponseEntity<?> response = produtosController.save(produto, bindingResult);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("Preço deve ser positivo"));
        verify(produtosService, times(1)).save(any(Produtos.class));
        verify(bindingResult, times(1)).hasErrors();
    }

    @Test
    @DisplayName("UNITÁRIO - Deve retornar lista vazia para nome inexistente")
    void testFindByNomeInexistente() {
        when(produtosService.findByNomeIgnoreCaseStartingWith("XPTO"))
            .thenReturn(Collections.emptyList());

        ResponseEntity<List<Produtos>> response = produtosController.findByNome("XPTO");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    @DisplayName("UNITÁRIO - Deve retornar lista vazia para categoria inexistente")
    void testFindByCategoriaInexistente() {
        when(produtosService.findByCategoriaIgnoreCaseStartingWith("Roupas"))
            .thenReturn(Collections.emptyList());

        ResponseEntity<List<Produtos>> response = produtosController.findByCategoria("Roupas");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    @DisplayName("UNITÁRIO - Deve verificar atualização com ID correto")
    void testUpdateComIdCorreto() {
        when(produtosService.update(any(Produtos.class), eq(1L))).thenReturn("OK");

        ResponseEntity<String> response = produtosController.update(produto, 1L);

        assertEquals(1L, produto.getId()); // Verifica se o ID foi mantido
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("UNITÁRIO - Deve rejeitar quantidade negativa em estoque")
    void testQuantidadeEstoqueNegativa() {
        
        produto.setQuantidade(-5);
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false); 

        ResponseEntity<?> response = produtosController.save(produto, bindingResult);


        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("não pode ser negativa"));
        

        verify(produtosService, never()).save(any());
    }

    @Test
    @DisplayName("UNITÁRIO - Deve aceitar quantidade zero em estoque")
    void testQuantidadeEstoqueZero() {
        produto.setQuantidade(0);
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);
        when(produtosService.save(any(Produtos.class))).thenReturn("Produto salvo com sucesso");

        ResponseEntity<?> response = produtosController.save(produto, bindingResult);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Produto salvo com sucesso", response.getBody());
        verify(produtosService, times(1)).save(produto);
        verify(bindingResult, times(1)).hasErrors();
    }
}
