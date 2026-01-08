package com.delas.api.controller;

import com.delas.api.dto.ServicosRequestDTO;
import com.delas.api.dto.ServicosResponseDTO;
import com.delas.api.model.ServicosModel;
import com.delas.api.service.ServicosService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/servicos")
public class ServicosController {

    @Autowired
    private ServicosService servicosService;

    @PostMapping
    public ResponseEntity<ServicosResponseDTO> createServico(
            @Valid @RequestBody ServicosRequestDTO servicoDTO,
            @RequestParam Long usuarioId) {
        ServicosModel novoServico = servicosService.save(servicoDTO, usuarioId);
        return ResponseEntity.status(201).body(ServicosResponseDTO.fromModel(novoServico));
    }

    @GetMapping
    public ResponseEntity<List<ServicosResponseDTO>> getAllServicos() {
        List<ServicosResponseDTO> servicos = servicosService.findAll()
                .stream()
                .map(ServicosResponseDTO::fromModel)
                .collect(Collectors.toList());
        if (servicos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(servicos);
    }

    @GetMapping("/ordenados-por-nota")
    public ResponseEntity<List<ServicosResponseDTO>> getServicosOrdenadosPorNota(
            @RequestParam(defaultValue = "false") boolean ascending) {
        List<ServicosResponseDTO> servicosOrdenados = servicosService.findAllOrderByNota(ascending)
                .stream()
                .map(ServicosResponseDTO::fromModel)
                .collect(Collectors.toList());
        if (servicosOrdenados.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(servicosOrdenados);
    }

    @GetMapping("/after")
    public ResponseEntity<List<ServicosResponseDTO>> getServicosAfter(@RequestParam String dataInicial) {
        LocalDateTime data = LocalDateTime.parse(dataInicial);
        List<ServicosResponseDTO> servicos = servicosService.findByDataCriacaoAfter(data)
                .stream()
                .map(ServicosResponseDTO::fromModel)
                .collect(Collectors.toList());
        return ResponseEntity.ok(servicos);
    }

    @GetMapping("/preco")
    public ResponseEntity<List<ServicosResponseDTO>> buscarPorPreco(
            @RequestParam("precoMinimo") BigDecimal precoMinimo,
            @RequestParam("precoMaximo") BigDecimal precoMaximo) {
        List<ServicosResponseDTO> servicos = servicosService.buscarServicosPorPreco(precoMinimo, precoMaximo)
                .stream()
                .map(ServicosResponseDTO::fromModel)
                .collect(Collectors.toList());
        return ResponseEntity.ok(servicos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServicosResponseDTO> getServicoById(@PathVariable Long id) {
        try {
            ServicosModel servico = servicosService.findById(id);
            return ResponseEntity.ok(ServicosResponseDTO.fromModel(servico));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServicosResponseDTO> updateServico(
            @PathVariable Long id,
            @Valid @RequestBody ServicosRequestDTO servicoDetails) {
        try {
            ServicosModel updatedServico = servicosService.update(id, servicoDetails);
            return ResponseEntity.ok(ServicosResponseDTO.fromModel(updatedServico));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).build();
        }
    }

    @GetMapping("/categoria")
    public ResponseEntity<List<ServicosResponseDTO>> getServicosByCategoria(@RequestParam String categoria) {
        List<ServicosResponseDTO> servicos = servicosService.findByCategoriaContaining(categoria)
                .stream()
                .map(ServicosResponseDTO::fromModel)
                .collect(Collectors.toList());
        if (servicos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(servicos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServico(@PathVariable Long id) {
        boolean isDeleted = servicosService.deleteById(id);
        return isDeleted
                ? ResponseEntity.status(204).build()
                : ResponseEntity.status(404).build();
    }
}
