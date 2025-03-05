package app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.entity.Animais;
import app.repository.AnimaisRepository;

@Service
public class AnimaisService {
    
    @Autowired
    private AnimaisRepository animaisRepository;

    public String save(Animais animais) {
        this.animaisRepository.save(animais);  // Save will handle both INSERT and UPDATE depending on the ID
        return "Animal salvo com sucesso";
    }

    public String update(Animais animais, long id) {
       
        if (!animaisRepository.existsById(id)) {
            return "Animal não encontrado para atualização";
        }

        animais.setId(id); 
        this.animaisRepository.save(animais);  
        return "Animal alterado com sucesso";
    }

    public String delete(long id) {
       
        if (!animaisRepository.existsById(id)) {
            return "Animal não encontrado para exclusão";
        }
        
        this.animaisRepository.deleteById(id);  // Deleta o animal pelo ID
        return "Animal deletado com sucesso!";
    }

    public List<Animais> findByNomeCompleto(String nome) {
        return this.animaisRepository.findByNomeCompletoStartingWith(nome);  
    }
}
