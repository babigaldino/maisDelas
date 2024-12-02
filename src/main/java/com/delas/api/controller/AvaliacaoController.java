package com.delas.api.controller;

import com.delas.api.model.AvaliacaoModel;
import com.delas.api.repository.AvaliacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/avaliacao")
public class AvaliacaoController {

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    // Criar uma avaliação
    @PostMapping
    public ResponseEntity<AvaliacaoModel> createAvaliacao(@RequestBody AvaliacaoModel avaliacao) {
        AvaliacaoModel savedAvaliacao = avaliacaoRepository.save(avaliacao);
        return new ResponseEntity<>(savedAvaliacao, HttpStatus.CREATED);
    }

    // Listar todas as avaliações
    @GetMapping
    public List<AvaliacaoModel> getAllAvaliacoes() {
        return avaliacaoRepository.findAll();
    }

    // Buscar avaliação por ID
    @GetMapping("/{id}")
    public ResponseEntity<AvaliacaoModel> getAvaliacaoById(@PathVariable Long id) {
        Optional<AvaliacaoModel> avaliacao = avaliacaoRepository.findById(id);
        if (avaliacao.isPresent()) {
            return ResponseEntity.ok(avaliacao.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    // Atualizar uma avaliação
    @PutMapping("/{id}")
    public ResponseEntity<AvaliacaoModel> updateAvaliacao(@PathVariable Long id, @RequestBody AvaliacaoModel avaliacaoDetails) {
        Optional<AvaliacaoModel> avaliacaoOptional = avaliacaoRepository.findById(id);
        if (avaliacaoOptional.isPresent()) {
            AvaliacaoModel avaliacao = avaliacaoOptional.get();
            avaliacao.setNota(avaliacaoDetails.getNota());
            // Outras atualizações, se necessárias
            AvaliacaoModel updatedAvaliacao = avaliacaoRepository.save(avaliacao);
            return ResponseEntity.ok(updatedAvaliacao);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    // Deletar uma avaliação
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAvaliacao(@PathVariable Long id) {
        if (avaliacaoRepository.existsById(id)) {
            avaliacaoRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
