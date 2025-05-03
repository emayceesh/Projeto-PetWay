package app.controller.test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
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

import app.controller.AnimaisController;
import app.entity.Animais;
import app.service.AnimaisService;

@ExtendWith(MockitoExtension.class)
class AnimaisControllerTest {

    @Mock
    private AnimaisService animaisService;

    @InjectMocks
    private AnimaisController animaisController;

    private Animais animal;

    @BeforeEach
    void setUp() {
        animal = new Animais();
        animal.setId(1L);
        animal.setNomeAnimal("Rex");
        animal.setRaca("Labrador");
       
        // Adicione outros campos necessários conforme sua entidade
    }

    // Testes de Integração
    @Test
    @DisplayName("INTEGRAÇÃO - Deve salvar animal válido")
    void testSaveAnimalValido() {
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);
        when(animaisService.save(any(Animais.class))).thenReturn("Animal salvo com sucesso");

        ResponseEntity<?> response = animaisController.save(animal, bindingResult);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Animal salvo com sucesso", response.getBody());
        verify(animaisService).save(any(Animais.class));
    }

    @Test
    @DisplayName("INTEGRAÇÃO - Deve retornar erro ao salvar animal inválido")
    void testSaveAnimalInvalido() {
        Animais animalInvalido = new Animais();
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(true);
        FieldError erro = new FieldError("animais", "nomeAnimal", "Nome obrigatório");
        when(bindingResult.getFieldErrors()).thenReturn(List.of(erro));

        ResponseEntity<?> response = animaisController.save(animalInvalido, bindingResult);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Nome obrigatório", response.getBody());
        verify(animaisService, never()).save(any());
    }

    @Test
    @DisplayName("INTEGRAÇÃO - Deve atualizar animal válido")
    void testUpdateAnimalValido() {
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);
        when(animaisService.update(any(Animais.class), eq(1L))).thenReturn("Animal atualizado com sucesso");

        ResponseEntity<?> response = animaisController.update(animal, bindingResult, 1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Animal atualizado com sucesso", response.getBody());
        verify(animaisService).update(any(Animais.class), eq(1L));
    }

    @Test
    @DisplayName("INTEGRAÇÃO - Deve deletar animal com sucesso")
    void testDeleteAnimal() {
        when(animaisService.delete(1L)).thenReturn("Animal deletado com sucesso");

        ResponseEntity<String> response = animaisController.delete(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Animal deletado com sucesso", response.getBody());
    }

    @Test
    @DisplayName("INTEGRAÇÃO - Deve buscar animal por ID")
    void testFindAnimalById() {
        when(animaisService.findById(1L)).thenReturn(animal);

        ResponseEntity<Animais> response = animaisController.findById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Rex", response.getBody().getNomeAnimal());
    }

    @Test
    @DisplayName("INTEGRAÇÃO - Deve listar todos animais")
    void testFindAllAnimais() {
        when(animaisService.findAll()).thenReturn(Arrays.asList(animal));

        ResponseEntity<List<Animais>> response = animaisController.findAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    @DisplayName("INTEGRAÇÃO - Deve buscar animais por nome")
    void testFindByNomeAnimal() {
        when(animaisService.findByNomeAnimalIgnoreCaseStartingWith("Rex")).thenReturn(Arrays.asList(animal));

        ResponseEntity<List<Animais>> response = animaisController.findByNomeAnimal("Rex");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Labrador", response.getBody().get(0).getRaca());
    }

    @Test
    @DisplayName("INTEGRAÇÃO - Deve buscar animais por raça")
    void testFindByRaca() {
        when(animaisService.findByRacaIgnoreCaseContaining("lab")).thenReturn(Arrays.asList(animal));

        ResponseEntity<List<Animais>> response = animaisController.findByRacaIgnoreCaseContaining("lab");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Rex", response.getBody().get(0).getNomeAnimal());
    }

    @Test
    @DisplayName("INTEGRAÇÃO - Deve buscar animais por nome e raça")
    void testFindByNomeAndRaca() {
        when(animaisService.findByNomeAnimalAndRacaIgnoreCase("Rex", "Labrador")).thenReturn(Arrays.asList(animal));

        ResponseEntity<List<Animais>> response = animaisController.findByNomeAnimalAndRaca("Rex", "Labrador");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    // Teste Unitário
    @Test
    @DisplayName("UNITÁRIO - Deve verificar chamada ao serviço de salvar")
    void testSaveServiceCall() {
        when(animaisService.save(any(Animais.class))).thenReturn("Animal salvo com sucesso");

        String resultado = animaisService.save(animal);

        assertEquals("Animal salvo com sucesso", resultado);
        verify(animaisService).save(animal);
    }
}