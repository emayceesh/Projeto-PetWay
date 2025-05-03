package app.service.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import app.entity.Servicos;
import app.repository.ServicosRepository;
import app.service.ServicosService;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class ServicosServiceTest {

    @Mock
    private ServicosRepository servicosRepository;

    @InjectMocks
    private ServicosService servicosService;

    private Servicos servico;

    @BeforeEach
    void setUp() {
        servico = new Servicos();
        servico.setId(1L);
        servico.setNomeServico("Banho Completo");
        servico.setPreco(new BigDecimal("150.00"));
        servico.setDisponivel(true);
    }

 //TESTES DE INTEGRAÇÃO 
    @Test
    @DisplayName("INTEGRAÇÃO - Deve salvar serviço com sucesso")
    void testSaveServico() {
        when(servicosRepository.save(any(Servicos.class))).thenReturn(servico);
        
        String resultado = servicosService.save(servico);
        
        assertEquals("Serviço cadastrado com sucesso!", resultado);
        verify(servicosRepository).save(servico);
    }

    @Test
    @DisplayName("INTEGRAÇÃO - Deve atualizar serviço existente")
    void testUpdateServico() {
        when(servicosRepository.save(any(Servicos.class))).thenReturn(servico);
        
        String resultado = servicosService.update(servico, 1L);
        
        assertEquals("Serviço alterado com sucesso!", resultado);
        assertEquals(1L, servico.getId());
    }

    @Test
    @DisplayName("INTEGRAÇÃO - Deve deletar serviço existente")
    void testDeleteServico() {
        doNothing().when(servicosRepository).deleteById(1L);
        
        String resultado = servicosService.delete(1L);
        
        assertEquals("Serviço deletado com sucesso!", resultado);
        verify(servicosRepository).deleteById(1L);
    }

    @Test
    @DisplayName("INTEGRAÇÃO - Deve listar todos os serviços")
    void testFindAllServicos() {
        when(servicosRepository.findAll()).thenReturn(Arrays.asList(servico));
        
        List<Servicos> resultado = servicosService.findAll();
        
        assertEquals(1, resultado.size());
        assertEquals("Banho Completo", resultado.get(0).getNomeServico());
    }

    @Test
    @DisplayName("INTEGRAÇÃO - Deve buscar serviço por ID")
    void testFindById() {
        when(servicosRepository.findById(1L)).thenReturn(Optional.of(servico));
        
        Servicos resultado = servicosService.findById(1L);
        
        assertEquals(new BigDecimal("150.00"), resultado.getPreco());
    }

    @Test
    @DisplayName("INTEGRAÇÃO - Deve buscar serviços por nome (case-insensitive)")
    void testFindByNome() {
        when(servicosRepository.findByNomeServicoIgnoreCaseStartingWith("banho"))
            .thenReturn(Arrays.asList(servico));
        
        List<Servicos> resultado = servicosService.findByNomeServicosIgnoreCaseStartingWith("banho");
        
        assertEquals(1, resultado.size());
    }

    @Test
    @DisplayName("INTEGRAÇÃO - Deve buscar serviços por disponibilidade")
    void testFindByDisponibilidade() {
        when(servicosRepository.findByDisponivel(true)).thenReturn(Arrays.asList(servico));
        
        List<Servicos> resultado = servicosService.findByDisponivel(true);
        
        assertTrue(resultado.get(0).getDisponivel());
    }

    @Test
    @DisplayName("INTEGRAÇÃO - Deve retornar lista vazia para nome inexistente")
    void testFindByNomeNaoEncontrado() {
        when(servicosRepository.findByNomeServicoIgnoreCaseStartingWith("xpto"))
            .thenReturn(Arrays.asList());
        
        List<Servicos> resultado = servicosService.findByNomeServicosIgnoreCaseStartingWith("xpto");
        
        assertTrue(resultado.isEmpty());
    }

    @Test
    @DisplayName("INTEGRAÇÃO - Deve retornar lista vazia para serviços indisponíveis")
    void testFindByDisponibilidadeFalse() {
        when(servicosRepository.findByDisponivel(false)).thenReturn(Arrays.asList());
        
        List<Servicos> resultado = servicosService.findByDisponivel(false);
        
        assertTrue(resultado.isEmpty());
    }
    
 //TESTES UNITÁRIOS
    @Test
    @DisplayName("UNITÁRIO - Deve lançar exceção ao salvar serviço nulo")
    void testSaveServicoNulo() {
        // Act & Assert
        NullPointerException exception = assertThrows(
            NullPointerException.class,
            () -> servicosService.save(null)
        );
        
        assertEquals("Serviço não pode ser nulo", exception.getMessage());
    }

    @Test
    @DisplayName("UNITÁRIO - Deve validar campos obrigatórios ao salvar")
    void testSaveServicoCamposObrigatorios() {
        Servicos servicoInvalido = new Servicos();
        servicoInvalido.setNomeServico(null);
        
        assertThrows(Exception.class, () -> servicosService.save(servicoInvalido));
    }

    @Test
    @DisplayName("UNITÁRIO - Deve verificar atualização com ID correto")
    void testUpdateComIdCorreto() {
        servicosService.update(servico, 1L);
        assertEquals(1L, servico.getId());
    }

    @Test
    @DisplayName("UNITÁRIO - Deve garantir que serviços indisponíveis não são retornados")
    void testServicosIndisponiveis() {
        servico.setDisponivel(false);
        when(servicosRepository.findByDisponivel(false)).thenReturn(Arrays.asList(servico));
        
        List<Servicos> resultado = servicosService.findByDisponivel(false);
        assertFalse(resultado.isEmpty());
        assertFalse(resultado.get(0).getDisponivel());
    }
    
}