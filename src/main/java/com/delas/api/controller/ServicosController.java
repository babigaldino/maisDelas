package com.delas.api.controller;

import com.delas.api.model.ServicosModel;
import com.delas.api.service.ServicosService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        return ResponseEntity.ok(servicos); // Código 200
    }

    // Obter serviços ordenados por nota
    @GetMapping("/ordenados-por-nota")
    public ResponseEntity<List<ServicosModel>> getServicosOrdenadosPorNota(@RequestParam(defaultValue = "false") boolean ascending) {
        List<ServicosModel> servicosOrdenados = servicosService.findAllOrderByNota(ascending);
        if (servicosOrdenados.isEmpty()) {
            return ResponseEntity.noContent().build(); // Código 204 se não houver serviços
        }
        return ResponseEntity.ok(servicosOrdenados); // Código 200
    }

    // Obter um serviço específico por ID
    @GetMapping("/{id}")
    public ResponseEntity<ServicosModel> getServicoById(@PathVariable Long id) {
        try {
            ServicosModel servico = servicosService.findById(id);
            return ResponseEntity.ok(servico); // Código 200 para sucesso
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(null); // Código 404 se não encontrar
        }
    }

    // Atualizar um serviço
    @PutMapping("/{id}")
    public ResponseEntity<ServicosModel> updateServico(@PathVariable Long id, @Valid @RequestBody ServicosModel servicoDetails) {
        try {
            ServicosModel updatedServico = servicosService.update(id, servicoDetails);
            return ResponseEntity.ok(updatedServico); // Código 200 para sucesso
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).build(); // Código 404 se não encontrar
        }
    }

    // Atualizar apenas a nota de um serviço
    @PatchMapping("/{id}/nota")
    public ResponseEntity<ServicosModel> updateNotaServico(@PathVariable Long id, @RequestParam Double nota) {
        try {
            ServicosModel updatedServico = servicosService.updateNota(id, nota);
            return ResponseEntity.ok(updatedServico); // Código 200 para sucesso
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(null); // Código 400 se a nota estiver fora do intervalo
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).build(); // Código 404 se não encontrar
        }
    }

    // Buscar serviços por categoria (com suporte a busca parcial) - Nova funcionalidade
    @GetMapping("/categoria")
    public ResponseEntity<List<ServicosModel>> getServicosByCategoria(@RequestParam String categoria) {
        List<ServicosModel> servicos = servicosService.findByCategoriaContaining(categoria);
        if (servicos.isEmpty()) {
            return ResponseEntity.noContent().build(); // Código 204 se não encontrar resultados
        }
        return ResponseEntity.ok(servicos);
    }

    // Deletar um serviço
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServico(@PathVariable Long id) {
        boolean isDeleted = servicosService.deleteById(id);
        return isDeleted
                ? ResponseEntity.status(204).build() // Código 204 para sucesso (sem conteúdo)
                : ResponseEntity.status(404).build(); // Código 404 se não encontrar
    }
}
