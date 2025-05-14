package app.controller.test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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

import app.controller.AgendamentoController;
import app.entity.Agendamento;
import app.entity.Cliente;
import app.service.AgendamentoService;

@ExtendWith(MockitoExtension.class)
class AgendamentoControllerTest {

    @Mock
    private AgendamentoService agendamentoService;

    @InjectMocks
    private AgendamentoController agendamentoController;

    private Agendamento agendamento;
    private final LocalDateTime now = LocalDateTime.now();

    @BeforeEach
    void setUp() {
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        
        agendamento = new Agendamento();
        agendamento.setId(1L);
        agendamento.setDataHora(now);
        agendamento.setCliente(cliente);
        agendamento.setBuscarEntregar(false);
        agendamento.setObservacoes("Observação teste");
    }

    // Testes de Integração
   

    @Test
    @DisplayName("INTEGRAÇÃO - Deve atualizar agendamento com sucesso")
    void testUpdateAgendamento() {
        when(agendamentoService.update(any(Agendamento.class), eq(1L))).thenReturn("Agendamento atualizado com sucesso!");

        ResponseEntity<String> response = agendamentoController.update(agendamento, 1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Agendamento atualizado com sucesso!", response.getBody());
    }

    @Test
    @DisplayName("INTEGRAÇÃO - Deve retornar NOT_FOUND para atualização de ID inexistente")
    void testUpdateAgendamentoInexistente() {
        when(agendamentoService.update(any(Agendamento.class), eq(99L)))
            .thenThrow(new RuntimeException("Agendamento não encontrado"));

        assertThrows(RuntimeException.class, () -> 
            agendamentoController.update(agendamento, 99L));
    }

    @Test
    @DisplayName("INTEGRAÇÃO - Deve deletar agendamento com sucesso")
    void testDeleteAgendamento() {
        when(agendamentoService.delete(1L)).thenReturn("Agendamento deletado com sucesso!");

        ResponseEntity<String> response = agendamentoController.delete(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Agendamento deletado com sucesso!", response.getBody());
    }

    @Test
    @DisplayName("INTEGRAÇÃO - Deve retornar todos os agendamentos")
    void testFindAllAgendamentos() {
        when(agendamentoService.findAll()).thenReturn(List.of(agendamento));

        ResponseEntity<List<Agendamento>> response = agendamentoController.findAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    @DisplayName("INTEGRAÇÃO - Deve retornar agendamento por ID")
    void testFindAgendamentoById() {
        when(agendamentoService.findById(1L)).thenReturn(Optional.of(agendamento));

        ResponseEntity<Agendamento> response = agendamentoController.findById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(now, response.getBody().getDataHora());
    }

    @Test
    @DisplayName("INTEGRAÇÃO - Deve retornar NOT_FOUND para ID inexistente")
    void testFindByIdInexistente() {
        when(agendamentoService.findById(99L)).thenReturn(Optional.empty());

        ResponseEntity<Agendamento> response = agendamentoController.findById(99L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(agendamentoService, times(1)).findById(99L);
    }

    @Test
    @DisplayName("INTEGRAÇÃO - Deve buscar agendamentos entre datas")
    void testBuscarEntreDatas() {
        String start = "2023-01-01T00:00:00";
        String end = "2023-12-31T23:59:59";
        
        when(agendamentoService.findByDataHoraBetween(any(LocalDateTime.class), any(LocalDateTime.class)))
            .thenReturn(List.of(agendamento));

        ResponseEntity<List<Agendamento>> response = agendamentoController.buscarAgendamentosEntreDatas(start, end);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    @DisplayName("INTEGRAÇÃO - Deve retornar NOT_FOUND para busca entre datas sem resultados")
    void testBuscarEntreDatasVazio() {
        String start = "2024-01-01T00:00:00";
        String end = "2024-12-31T23:59:59";
        
        when(agendamentoService.findByDataHoraBetween(any(LocalDateTime.class), any(LocalDateTime.class)))
            .thenReturn(Collections.emptyList());

        ResponseEntity<List<Agendamento>> response = agendamentoController.buscarAgendamentosEntreDatas(start, end);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @DisplayName("INTEGRAÇÃO - Deve buscar agendamentos por cliente")
    void testBuscarPorCliente() {
        when(agendamentoService.buscarPorCliente(1L)).thenReturn(List.of(agendamento));

        ResponseEntity<List<Agendamento>> response = agendamentoController.buscarAgendamentosPorCliente(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    @DisplayName("INTEGRAÇÃO - Deve retornar NOT_FOUND para cliente sem agendamentos")
    void testBuscarPorClienteVazio() {
        when(agendamentoService.buscarPorCliente(99L)).thenReturn(Collections.emptyList());

        ResponseEntity<List<Agendamento>> response = agendamentoController.buscarAgendamentosPorCliente(99L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @DisplayName("INTEGRAÇÃO - Deve buscar agendamentos por serviço")
    void testBuscarPorServico() {
        when(agendamentoService.buscarPorNomeServico("Banho")).thenReturn(List.of(agendamento));

        ResponseEntity<List<Agendamento>> response = agendamentoController.buscarPorNomeServico("Banho");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

   

  

        // Nota: A validação precisa ser ajustada no controller para usar BindingResult
        // ResponseEntity<?> response = agendamentoController.save(agendamentoInvalido, bindingResult);
        // Implementação real precisa incluir BindingResult no método save
    

    @Test
    @DisplayName("UNITÁRIO - Deve verificar deleção com ID correto")
    void testDeleteServiceCall() {
        agendamentoController.delete(1L);
        verify(agendamentoService, times(1)).delete(1L);
    }

    @Test
    @DisplayName("UNITÁRIO - Deve buscar por intervalo de datas correto")
    void testBuscaEntreDatasServiceCall() {
        String start = "2023-01-01T00:00:00";
        String end = "2023-01-02T00:00:00";
        
        agendamentoController.buscarAgendamentosEntreDatas(start, end);
        
        verify(agendamentoService).findByDataHoraBetween(
            LocalDateTime.parse(start),
            LocalDateTime.parse(end)
        );
    }
}