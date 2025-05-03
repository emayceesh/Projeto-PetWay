package app.controller.test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
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

import app.controller.ClienteController;
import app.entity.Cliente;
import app.service.ClienteService;

@ExtendWith(MockitoExtension.class)
class ClienteControllerTest {

    @Mock
    private ClienteService clienteService;

    @InjectMocks
    private ClienteController clienteController;

    private Cliente cliente;

    @BeforeEach
    void setUp() {
        cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNomeCliente("João Silva");
        cliente.setCpf("12345678901");
        cliente.setCelular("11999999999");
        cliente.setEndereco("Rua Teste, 123");
        cliente.setCadastroCompleto(true);
    }

    // Testes de Integração

    @Test
    @DisplayName("INTEGRAÇÃO - Deve salvar cliente com sucesso")
    void testSaveClienteValido() {
        BindingResult bindingResult = mock(BindingResult.class);
        lenient().when(bindingResult.hasErrors()).thenReturn(false);
        when(clienteService.save(any(Cliente.class))).thenReturn("Cliente salvo com sucesso");

        ResponseEntity<String> response = clienteController.save(cliente, bindingResult);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Cliente salvo com sucesso", response.getBody());
        verify(clienteService).save(any(Cliente.class));
    }

    @Test
    @DisplayName("INTEGRAÇÃO - Deve retornar erro ao salvar cliente inválido")
    void testSaveClienteInvalido() {
        
        Cliente clienteInvalido = new Cliente();
        
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(true);
        when(bindingResult.getFieldErrors()).thenReturn(
            List.of(new FieldError("cliente", "nomeCliente", "Nome não pode estar vazio")));

        ResponseEntity<String> response = clienteController.save(clienteInvalido, bindingResult);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().contains("Nome não pode estar vazio"));
        
        verify(clienteService, never()).save(any());
    }

    @Test
    @DisplayName("INTEGRAÇÃO - Deve atualizar cliente com sucesso")
    void testUpdateCliente() {
        when(clienteService.update(any(Cliente.class), eq(1L))).thenReturn("Cliente atualizado com sucesso");

        ResponseEntity<String> response = clienteController.update(cliente, 1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Cliente atualizado com sucesso", response.getBody());
    }

    @Test
    @DisplayName("INTEGRAÇÃO - Deve deletar cliente com sucesso")
    void testDeleteCliente() {
        when(clienteService.delete(1L)).thenReturn("Cliente deletado com sucesso");

        ResponseEntity<String> response = clienteController.delete(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Cliente deletado com sucesso", response.getBody());
    }

    @Test
    @DisplayName("INTEGRAÇÃO - Deve retornar todos os clientes")
    void testFindAllClientes() {
        List<Cliente> clientes = Arrays.asList(cliente);
        when(clienteService.findAll()).thenReturn(clientes);

        ResponseEntity<List<Cliente>> response = clienteController.findAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals("João Silva", response.getBody().get(0).getNomeCliente());
    }

    @Test
    @DisplayName("INTEGRAÇÃO - Deve retornar cliente por ID")
    void testFindClienteById() {
        when(clienteService.findById(1L)).thenReturn(cliente);

        ResponseEntity<Cliente> response = clienteController.findById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("João Silva", response.getBody().getNomeCliente());
    }

    @Test
    @DisplayName("INTEGRAÇÃO - Deve retornar clientes por nome")
    void testFindClientesByNome() {
        List<Cliente> clientes = Arrays.asList(cliente);
        when(clienteService.findByNomeClienteIgnoreCaseStartingWith("João")).thenReturn(clientes);

        ResponseEntity<List<Cliente>> response = clienteController.findByNomeClienteIgnoreCaseStartingWith("João");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals("João Silva", response.getBody().get(0).getNomeCliente());
    }

    // Teste Unitário
    @Test
    @DisplayName("UNITÁRIO - Deve salvar cliente no serviço")
    void testSaveClienteService() {
        when(clienteService.save(any(Cliente.class))).thenReturn("Cliente salvo com sucesso");

        String resultado = clienteService.save(cliente);

        assertEquals("Cliente salvo com sucesso", resultado);
        verify(clienteService, times(1)).save(cliente);
    }
}