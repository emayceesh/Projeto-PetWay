package app.service.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import app.entity.Cliente;
import app.repository.ClienteRepository;
import app.service.ClienteService;

@SpringBootTest
public class ClienteServiceTests {

    @Autowired
    private ClienteService clienteService;

    @MockitoBean
    private ClienteRepository clienteRepository;

    private Cliente cliente;

    @BeforeEach
    void setUp() {
        cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNomeCliente("João Silva");
        cliente.setCpf("12345678901");
        cliente.setCelular("11999999999");
        cliente.setEndereco("Rua Teste");
    }

    //TESTES DE INTEGRAÇÃO
    @Test
    @DisplayName("INTEGRAÇÃO - Deve salvar cliente com sucesso")
    void testSaveClienteValido() {
        when(clienteRepository.findByCpfStartingWith(anyString())).thenReturn(Collections.emptyList());
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        String resultado = clienteService.save(cliente);

        assertEquals("Cliente cadastrado com sucesso!", resultado);
        verify(clienteRepository, times(1)).save(cliente);
    }

    @Test
    @DisplayName("INTEGRAÇÃO - Deve lançar exceção ao salvar CPF duplicado")
    void testSaveClienteComCpfDuplicado() {
        when(clienteRepository.findByCpfStartingWith(anyString())).thenReturn(List.of(cliente));

        assertThrows(RuntimeException.class, () -> clienteService.save(cliente));
        verify(clienteRepository, never()).save(any());
    }

    @Test
    @DisplayName("INTEGRAÇÃO - Deve retornar cliente por ID")
    void testFindById() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

        Cliente encontrado = clienteService.findById(1L);

        assertEquals("João Silva", encontrado.getNomeCliente());
        verify(clienteRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("INTEGRAÇÃO - Deve retornar lista vazia quando não houver clientes")
    void testFindAllVazia() {
        when(clienteRepository.findAll()).thenReturn(Collections.emptyList());

        List<Cliente> clientes = clienteService.findAll();

        assertTrue(clientes.isEmpty());
        verify(clienteRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("INTEGRAÇÃO - Deve deletar cliente com sucesso")
    void testDeleteCliente() {
        doNothing().when(clienteRepository).deleteById(1L);

        String resultado = clienteService.delete(1L);

        assertEquals("Cliente excluido com sucesso!", resultado);
        verify(clienteRepository, times(1)).deleteById(1L);
    }
    
    //TESTES UNITÁRIOS
    @Test
    @DisplayName("UNITÁRIO - Deve atualizar cliente com sucesso")
    void testUpdateCliente() {
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        String resultado = clienteService.update(cliente, 1L);

        assertEquals("Cliente alterado com sucesso!", resultado);
        assertEquals(1L, cliente.getId()); // Verifica se o ID foi setado
        verify(clienteRepository, times(1)).save(cliente);
    }

    @Test
    @DisplayName("UNITÁRIO - Deve retornar lista por nome (case-insensitive)")
    void testFindByNomeIgnoreCase() {
        when(clienteRepository.findByNomeClienteIgnoreCaseStartingWith("maria"))
            .thenReturn(List.of(cliente));

        List<Cliente> clientes = clienteService.findByNomeClienteIgnoreCaseStartingWith("maria");

        assertEquals(1, clientes.size());
        assertEquals("João Silva", clientes.get(0).getNomeCliente());
    }

    @Test
    @DisplayName("UNITÁRIO - Deve marcar cadastro como incompleto sem telefone")
    void testVerificarTelefoneClienteIncompleto() {
        cliente.setTelefone(null);

        clienteService.verificarTelefoneCliente(cliente);

        assertFalse(cliente.getCadastroCompleto());
    }
}