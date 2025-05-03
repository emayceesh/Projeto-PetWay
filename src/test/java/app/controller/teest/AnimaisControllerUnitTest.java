package app.controller.teest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import app.controller.AnimaisController;
import app.entity.Animais;
import app.service.AnimaisService;

@ExtendWith(MockitoExtension.class)
class AnimaisControllerUnitTest {

    @Mock
    AnimaisService animaisService;

    @InjectMocks
    AnimaisController controller;

    MockMvc mockMvc;
    ObjectMapper mapper;

    @BeforeEach
    void setup() {
        mapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    @DisplayName("POST /api/animal/save should return OK and message")
    void testSaveAnimal() throws Exception {
        Animais dummy = new Animais();
        dummy.setNomeAnimal("Rex");
        dummy.setRaca("Labrador");
        dummy.setSexoAnimal("Masculino");
        dummy.setPorte("Medio");

        when(animaisService.save(any(Animais.class))).thenReturn("Animal salvo com sucesso");

        mockMvc.perform(post("/api/animal/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(dummy)))
            .andExpect(status().isOk())
            .andExpect(content().string("Animal salvo com sucesso"));
    }

    @Test
    @DisplayName("PUT /api/animal/update/{id} should return OK and message")
    void testUpdateAnimal() throws Exception {
        long id = 1L;
        Animais updated = new Animais();
        updated.setNomeAnimal("Rexito");
        updated.setRaca("Golden");
        updated.setSexoAnimal("Masculino");
        updated.setPorte("Grande");

        when(animaisService.update(any(Animais.class), eq(id))).thenReturn("Animal alterado com sucesso");

        mockMvc.perform(put("/api/animal/update/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(updated)))
            .andExpect(status().isOk())
            .andExpect(content().string("Animal alterado com sucesso"));
    }

    @Test
    @DisplayName("DELETE /api/animal/delete/{id} should return OK and message")
    void testDeleteAnimal() throws Exception {
        long id = 2L;
        when(animaisService.delete(id)).thenReturn("Animal deletado com sucesso!");

        mockMvc.perform(delete("/api/animal/delete/{id}", id))
            .andExpect(status().isOk())
            .andExpect(content().string("Animal deletado com sucesso!"));
    }

    @Test
    @DisplayName("GET /api/animal/findById/{id} should return animal JSON")
    void testFindById() throws Exception {
        long id = 3L;
        Animais a = new Animais(id, "Mia", "Poodle", "Feminino", "Pequeno", "Branco", null, null);
        when(animaisService.findById(id)).thenReturn(a);

        mockMvc.perform(get("/api/animal/findById/{id}", id))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(id))
            .andExpect(jsonPath("$.nomeAnimal").value("Mia"));
    }

    @Test
    @DisplayName("GET /api/animal/findByNome should return filtered list")
    void testFindByNome() throws Exception {
        String nome = "Re";
        List<Animais> list = Arrays.asList(new Animais(4L, "Rex", "Labrador", "Masculino", "Grande", "Preto", null, null));
        when(animaisService.findByNomeAnimalIgnoreCaseStartingWith(nome)).thenReturn(list);

        mockMvc.perform(get("/api/animal/findByNome").param("nomeAnimal", nome))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    @DisplayName("GET /api/animal/findByRaca should return filtered list")
    void testFindByRaca() throws Exception {
        String raca = "lab";
        List<Animais> list = Arrays.asList(new Animais(5L, "Bolt", "Labrador", "Masculino", "Medio", "Marrom", null, null));
        when(animaisService.findByRacaIgnoreCaseContaining(raca)).thenReturn(list);

        mockMvc.perform(get("/api/animal/findByRaca").param("raca", raca))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    @DisplayName("GET /api/animal/findByNomeAnimalAndRaca should return filtered list")
    void testFindByNomeAndRaca() throws Exception {
        String nome = "Mi";
        String raca = "Pood";
        List<Animais> list = Arrays.asList(new Animais(6L, "Mia", "Poodle", "Feminino", "Pequeno", "Branco", null, null));
        when(animaisService.findByNomeAnimalAndRacaIgnoreCase(nome, raca)).thenReturn(list);

        mockMvc.perform(get("/api/animal/findByNomeAnimalAndRaca")
                .param("nomeAnimal", nome)
                .param("raca", raca))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(1));
    }
}
