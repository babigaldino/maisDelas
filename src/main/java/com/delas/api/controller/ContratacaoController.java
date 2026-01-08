package com.delas.api.controller;

import com.delas.api.dto.ContratacaoRequestDTO;
import com.delas.api.dto.ContratacaoResponseDTO;
import com.delas.api.model.ContratacaoModel;
import com.delas.api.model.ServicosModel;
import com.delas.api.model.UsuarioModel;
import com.delas.api.repository.ContratacaoRepository;
import com.delas.api.repository.ServicosRepository;
import com.delas.api.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/contratacao")
public class ContratacaoController {

    @Autowired
    private ContratacaoRepository contratacaoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ServicosRepository servicosRepository;

    @PostMapping
    public ResponseEntity<ContratacaoResponseDTO> createContratacao(
            @Valid @RequestBody ContratacaoRequestDTO contratoDTO) {
        try {
            // Busca usuário e serviço
            UsuarioModel usuario = usuarioRepository.findById(contratoDTO.getUsuarioId())
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

            ServicosModel servico = servicosRepository.findById(contratoDTO.getServicoId())
                    .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));

            // Cria a contratação
            ContratacaoModel contratacao = new ContratacaoModel();
            contratacao.setUsuario(usuario);
            contratacao.setServico(servico);
            contratacao.setStatus(ContratacaoModel.StatusContratacao.valueOf(contratoDTO.getStatus()));
            contratacao.setComentarios(contratoDTO.getComentarios());

            ContratacaoModel saved = contratacaoRepository.save(contratacao);
            return ResponseEntity.status(201).body(ContratacaoResponseDTO.fromModel(saved));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<ContratacaoResponseDTO>> getAllContratacoes() {
        List<ContratacaoResponseDTO> contratacoes = contratacaoRepository.findAll()
                .stream()
                .map(ContratacaoResponseDTO::fromModel)
                .collect(Collectors.toList());
        if (contratacoes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(contratacoes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContratacaoResponseDTO> getContratacaoById(@PathVariable Long id) {
        return contratacaoRepository.findById(id)
                .map(contratacao -> ResponseEntity.ok(ContratacaoResponseDTO.fromModel(contratacao)))
                .orElseGet(() -> ResponseEntity.status(404).build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContratacaoResponseDTO> updateContratacao(
            @PathVariable Long id,
            @Valid @RequestBody ContratacaoRequestDTO contratoDetails) {
        try {
            ContratacaoModel contratacao = contratacaoRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Contratação não encontrada"));

            // ✅ CORRIGIDO: Usar setUsuario() e setServico()
            if (contratoDetails.getUsuarioId() != null) {
                UsuarioModel usuario = usuarioRepository.findById(contratoDetails.getUsuarioId())
                        .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
                contratacao.setUsuario(usuario);
            }

            if (contratoDetails.getServicoId() != null) {
                ServicosModel servico = servicosRepository.findById(contratoDetails.getServicoId())
                        .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));
                contratacao.setServico(servico);
            }

            if (contratoDetails.getStatus() != null) {
                contratacao.setStatus(ContratacaoModel.StatusContratacao.valueOf(contratoDetails.getStatus()));
            }

            if (contratoDetails.getComentarios() != null) {
                contratacao.setComentarios(contratoDetails.getComentarios());
            }

            ContratacaoModel updated = contratacaoRepository.save(contratacao);
            return ResponseEntity.ok(ContratacaoResponseDTO.fromModel(updated));
        } catch (Exception e) {
            return ResponseEntity.status(404).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContratacao(@PathVariable Long id) {
        if (contratacaoRepository.existsById(id)) {
            contratacaoRepository.deleteById(id);
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(404).build();
    }
}
