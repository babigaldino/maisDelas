package com.delas.api.controller;

import com.delas.api.dto.AvaliacaoRequestDTO;
import com.delas.api.dto.AvaliacaoResponseDTO;
import com.delas.api.model.AvaliacaoModel;
import com.delas.api.service.AvaliacaoService; 
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
    private AvaliacaoService avaliacaoService; 

    @PostMapping
    public ResponseEntity<AvaliacaoResponseDTO> createAvaliacao(
            @Valid @RequestBody AvaliacaoRequestDTO avaliacaoDTO) {
        try {
            System.out.println("🟢 [CONTROLLER] Recebeu POST /avaliacao");
            System.out.println("🟢 ContratacaoId: " + avaliacaoDTO.getContratacaoId() + ", Nota: " + avaliacaoDTO.getNota());
            
           
            AvaliacaoModel saved = avaliacaoService.save(avaliacaoDTO);
            
            return ResponseEntity.status(HttpStatus.CREATED).body(AvaliacaoResponseDTO.fromModel(saved));
        } catch (Exception e) {
            System.err.println("❌ Erro no controller: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<AvaliacaoResponseDTO>> getAllAvaliacoes() {
        List<AvaliacaoResponseDTO> avaliacoes = avaliacaoService.findAll()
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
        try {
            AvaliacaoModel avaliacao = avaliacaoService.findById(id);
            return ResponseEntity.ok(AvaliacaoResponseDTO.fromModel(avaliacao));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<AvaliacaoResponseDTO> updateAvaliacao(
            @PathVariable Long id,
            @Valid @RequestBody AvaliacaoRequestDTO avaliacaoDetails) {
        try {
           
            AvaliacaoModel updated = avaliacaoService.update(id, avaliacaoDetails);
            return ResponseEntity.ok(AvaliacaoResponseDTO.fromModel(updated));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAvaliacao(@PathVariable Long id) {
       
        boolean deleted = avaliacaoService.deleteById(id);
        if (deleted) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
