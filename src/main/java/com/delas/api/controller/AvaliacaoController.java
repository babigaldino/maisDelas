package com.delas.api.controller;

import com.delas.api.dto.AvaliacaoRequestDTO;
import com.delas.api.dto.AvaliacaoResponseDTO;
import com.delas.api.model.AvaliacaoModel;
import com.delas.api.model.ContratacaoModel;
import com.delas.api.repository.AvaliacaoRepository;
import com.delas.api.repository.ContratacaoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/avaliacao")
public class AvaliacaoController {

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    @Autowired
    private ContratacaoRepository contratacaoRepository;

    @PostMapping
    public ResponseEntity<AvaliacaoResponseDTO> createAvaliacao(
            @Valid @RequestBody AvaliacaoRequestDTO avaliacaoDTO) {
        try {
            ContratacaoModel contratacao = contratacaoRepository.findById(avaliacaoDTO.getContratacaoId())
                    .orElseThrow(() -> new RuntimeException("Contratação não encontrada"));

            AvaliacaoModel avaliacao = new AvaliacaoModel();
            avaliacao.setContratacao(contratacao);
            avaliacao.setNota(avaliacaoDTO.getNota());
            avaliacao.setComentario(avaliacaoDTO.getComentario());

            AvaliacaoModel saved = avaliacaoRepository.save(avaliacao);
            return ResponseEntity.status(201).body(AvaliacaoResponseDTO.fromModel(saved));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<AvaliacaoResponseDTO>> getAllAvaliacoes() {
        List<AvaliacaoResponseDTO> avaliacoes = avaliacaoRepository.findAll()
                .stream()
                .map(AvaliacaoResponseDTO::fromModel)
                .collect(Collectors.toList());
        if (avaliacoes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(avaliacoes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AvaliacaoResponseDTO> getAvaliacaoById(@PathVariable Long id) {
        return avaliacaoRepository.findById(id)
                .map(avaliacao -> ResponseEntity.ok(AvaliacaoResponseDTO.fromModel(avaliacao)))
                .orElseGet(() -> ResponseEntity.status(404).build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<AvaliacaoResponseDTO> updateAvaliacao(
            @PathVariable Long id,
            @Valid @RequestBody AvaliacaoRequestDTO avaliacaoDetails) {
        try {
            AvaliacaoModel avaliacao = avaliacaoRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Avaliação não encontrada"));

            if (avaliacaoDetails.getNota() != null) {
                avaliacao.setNota(avaliacaoDetails.getNota());
            }

            if (avaliacaoDetails.getComentario() != null) {
                avaliacao.setComentario(avaliacaoDetails.getComentario());
            }

            AvaliacaoModel updated = avaliacaoRepository.save(avaliacao);
            return ResponseEntity.ok(AvaliacaoResponseDTO.fromModel(updated));
        } catch (Exception e) {
            return ResponseEntity.status(404).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAvaliacao(@PathVariable Long id) {
        if (avaliacaoRepository.existsById(id)) {
            avaliacaoRepository.deleteById(id);
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(404).build();
    }
}
