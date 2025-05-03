package app.controller.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.lenient;

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

import app.controller.ServicosController;
import app.entity.Servicos;
import app.service.ServicosService;

@ExtendWith(MockitoExtension.class)
class ServicoControllerTest {

    @Mock
    private ServicosService servicosService;

    @InjectMocks
    private ServicosController servicosController;

    private Servicos servico;

    @BeforeEach
    void setUp() {
        servico = new Servicos();
        servico.setId(1L);
        servico.setNomeServico("Banho e Tosa");
        servico.setDisponivel(true);
        servico.setPreco(new BigDecimal("100.50"));
    }

    //TESTES DE INTEGRAÇÃO
    
    @Test
    @DisplayName("INTEGRAÇÃO - Deve salvar serviço com sucesso")
    void testSaveServicoValido() {
    	BindingResult bindingResult = mock(BindingResult.class);
        lenient().when(bindingResult.hasErrors()).thenReturn(false);
        when(servicosService.save(any(Servicos.class))).thenReturn("Serviço salvo com sucesso");

        // Act
        ResponseEntity<String> response = servicosController.save(servico, bindingResult);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Serviço salvo com sucesso", response.getBody());
        verify(servicosService).save(any(Servicos.class));
        verify(bindingResult).hasErrors(); // Verificação explícita para eliminar o warning
    }

    @Test
    @DisplayName("INTEGRAÇÃO - Deve retornar erro ao salvar serviço inválido")
    void testSaveServicoInvalido() {
        Servicos servicoInvalido = new Servicos();
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(true);
        when(bindingResult.getFieldErrors()).thenReturn(
            List.of(new FieldError("servico", "nomeServicos", "Nome é obrigatório")));

        ResponseEntity<String> response = servicosController.save(servicoInvalido, bindingResult);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().contains("Nome é obrigatório"));
        verify(servicosService, never()).save(any());
    }

    @Test
    @DisplayName("INTEGRAÇÃO - Deve atualizar serviço com sucesso")
    void testUpdateServico() {
        when(servicosService.update(any(Servicos.class), eq(1L))).thenReturn("Serviço atualizado");

        ResponseEntity<String> response = servicosController.update(servico, 1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Serviço atualizado", response.getBody());
    }

    @Test
    @DisplayName("INTEGRAÇÃO - Deve deletar serviço com sucesso")
    void testDeleteServico() {
        when(servicosService.delete(1L)).thenReturn("Serviço deletado");

        ResponseEntity<String> response = servicosController.delete(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Serviço deletado", response.getBody());
    }

    @Test
    @DisplayName("INTEGRAÇÃO - Deve retornar todos os serviços")
    void testFindAllServicos() {
        List<Servicos> servicos = Arrays.asList(servico);
        when(servicosService.findAll()).thenReturn(servicos);

        ResponseEntity<List<Servicos>> response = servicosController.findAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals("Banho e Tosa", response.getBody().get(0).getNomeServico());
    }

    @Test
    @DisplayName("INTEGRAÇÃO - Deve retornar serviço por ID")
    void testFindServicoById() {
        when(servicosService.findById(1L)).thenReturn(servico);

        ResponseEntity<Servicos> response = servicosController.findById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Banho e Tosa", response.getBody().getNomeServico());
    }

    @Test
    @DisplayName("INTEGRAÇÃO - Deve retornar serviços por disponibilidade")
    void testFindByDisponibilidade() {
        List<Servicos> servicos = Arrays.asList(servico);
        when(servicosService.findByDisponivel(true)).thenReturn(servicos);

        ResponseEntity<List<Servicos>> response = servicosController.findByDisponivel(true);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertTrue(response.getBody().get(0).getDisponivel());
    }

    //TESTES UNITARIOS

    @Test
    @DisplayName("UNITÁRIO - Deve retornar NOT_FOUND para serviço inexistente")
    void testFindByIdInexistente() {
        when(servicosService.findById(99L)).thenReturn(null);

        ResponseEntity<Servicos> response = servicosController.findById(99L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    @DisplayName("UNITÁRIO - Deve retornar lista vazia para nome inexistente")
    void testFindByNomeInexistente() {
        when(servicosService.findByNomeServicosIgnoreCaseStartingWith("Inexistente"))
            .thenReturn(Collections.emptyList());

        ResponseEntity<List<Servicos>> response = servicosController
            .findByNomeServicosIgnoreCaseStartingWith("Inexistente");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isEmpty());
    }
}
