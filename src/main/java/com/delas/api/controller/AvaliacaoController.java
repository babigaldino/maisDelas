

package com.delas.api.controller;
import com.delas.api.model.AvaliacaoModel;
import com.delas.api.repository.AvaliacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/avaliacao")
public class AvaliacaoController {

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    @PostMapping
    public AvaliacaoModel createAvaliacao(@RequestBody AvaliacaoModel avaliacao) {
        return avaliacaoRepository.save(avaliacao);
    }

    @GetMapping
    public List<AvaliacaoModel> getAllAvaliacoes() {
        return avaliacaoRepository.findAll();
    }

    @GetMapping("/{id}")
    public AvaliacaoModel getAvaliacaoById(@PathVariable Long id) {
        return avaliacaoRepository.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public AvaliacaoModel updateAvaliacao(@PathVariable Long id, @RequestBody AvaliacaoModel avaliacaoDetails) {
        AvaliacaoModel avaliacao = avaliacaoRepository.findById(id).orElse(null);
        if (avaliacao != null) {
            avaliacao.setNota(avaliacaoDetails.getNota());
            // Outras atualizações, se necessárias
            return avaliacaoRepository.save(avaliacao);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteAvaliacao(@PathVariable Long id) {
        avaliacaoRepository.deleteById(id);
    }
}
