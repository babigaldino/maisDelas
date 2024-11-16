package com.delas.api.controller;

import com.delas.api.model.ServicosModel;
import com.delas.api.service.ServicosService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/servicos")
public class ServicosController {

    @Autowired
    private ServicosService servicosService;

    // Criar um novo serviço
    @PostMapping
    public ResponseEntity<ServicosModel> createServico(@Valid @RequestBody ServicosModel servico) {
        ServicosModel novoServico = servicosService.save(servico);
        return ResponseEntity.status(201).body(novoServico); // Código 201 para criação
    }

    // Obter todos os serviços
    @GetMapping
    public ResponseEntity<List<ServicosModel>> getAllServicos() {
        List<ServicosModel> servicos = servicosService.findAll();
        if (servicos.isEmpty()) {
            return ResponseEntity.noContent().build(); // Código 204 se não houver serviços
        }
        return ResponseEntity.ok(servicos);
    }

    // Obter um serviço específico por ID
    @GetMapping("/{id}")
    public ResponseEntity<ServicosModel> getServicoById(@PathVariable Long id) {
        Optional<ServicosModel> servico = servicosService.findById(id);
        return servico
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(404).body(null)); // Código 404 se não encontrar
    }

    // Atualizar um serviço
    @PutMapping("/{id}")
    public ResponseEntity<ServicosModel> updateServico(@PathVariable Long id, @Valid @RequestBody ServicosModel servicoDetails) {
        Optional<ServicosModel> updatedServico = servicosService.update(id, servicoDetails);
        return updatedServico
                .map(ResponseEntity::ok) // Código 200 para sucesso
                .orElseGet(() -> ResponseEntity.status(404).body(null)); // Código 404 se não encontrar
    }

    // Deletar um serviço
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServico(@PathVariable Long id) {
        boolean isDeleted = servicosService.deleteById(id);
        if (isDeleted) {
            return ResponseEntity.status(204).build(); // Código 204 para sucesso (sem conteúdo)
        } else {
            return ResponseEntity.status(404).build(); // Código 404 se não encontrar
        }
    }
}
