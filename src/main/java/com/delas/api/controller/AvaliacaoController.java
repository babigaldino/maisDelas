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

}

