package com.delas.api.controller;

import com.delas.api.dto.FavoritoRequestDTO;
import com.delas.api.dto.FavoritoResponseDTO;
import com.delas.api.dto.UsuarioResponseDTO;
import com.delas.api.model.FavoritoModel;
import com.delas.api.model.ServicosModel;
import com.delas.api.model.UsuarioModel;
import com.delas.api.repository.ServicosRepository;
import com.delas.api.repository.UsuarioRepository;
import com.delas.api.service.FavoritoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/favorito")
public class FavoritoController {

    @Autowired
    private FavoritoService favoritoService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ServicosRepository servicosRepository;

    @GetMapping("/prestadoras/{usuarioId}")
    public ResponseEntity<List<UsuarioResponseDTO>> getPrestadorasFavoritas(@PathVariable Long usuarioId) {
        List<UsuarioResponseDTO> prestadoras = favoritoService.findPrestadorasFavoritas(usuarioId)
                .stream()
                .map(UsuarioResponseDTO::fromModel)
                .collect(Collectors.toList());
        return ResponseEntity.ok(prestadoras);
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addFavoritoByParams(
            @RequestParam Long usuarioId,
            @RequestParam Long servicoId) {
        try {
            favoritoService.addFavorito(usuarioId, servicoId);
            return ResponseEntity.status(201).build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    
    @DeleteMapping("/remove")
    public ResponseEntity<Void> removeFavoritoByParams(
            @RequestParam Long usuarioId,
            @RequestParam Long servicoId) {
        favoritoService.removeFavorito(usuarioId, servicoId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/check")
    public ResponseEntity<Boolean> checkFavorito(
            @RequestParam Long usuarioId,
            @RequestParam Long servicoId) {
        boolean isFavorito = favoritoService.isFavorito(usuarioId, servicoId);
        return ResponseEntity.ok(isFavorito);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<FavoritoResponseDTO>> getFavoritosByUsuario(@PathVariable Long usuarioId) {
        List<FavoritoResponseDTO> favoritos = favoritoService.findByUsuarioId(usuarioId)
                .stream()
                .map(FavoritoResponseDTO::fromModel)
                .collect(Collectors.toList());
        return ResponseEntity.ok(favoritos);
    }

    @PostMapping
    public ResponseEntity<FavoritoResponseDTO> createFavorito(@Valid @RequestBody FavoritoRequestDTO favoritoDTO) {
        try {
            UsuarioModel usuario = usuarioRepository.findById(favoritoDTO.getUsuarioId())
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

            ServicosModel servico = servicosRepository.findById(favoritoDTO.getServicoId())
                    .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));

            FavoritoModel favorito = new FavoritoModel();
            favorito.setUsuarioFavorito(usuario);
            favorito.setServicoFavorito(servico);

            FavoritoModel novoFavorito = favoritoService.save(favorito);
            return ResponseEntity.status(201).body(FavoritoResponseDTO.fromModel(novoFavorito));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<FavoritoResponseDTO>> getAllFavoritos() {
        List<FavoritoResponseDTO> favoritos = favoritoService.findAll()
                .stream()
                .map(FavoritoResponseDTO::fromModel)
                .collect(Collectors.toList());
        if (favoritos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(favoritos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FavoritoResponseDTO> getFavoritoById(@PathVariable Long id) {
        FavoritoModel favorito = favoritoService.findById(id);
        return favorito != null
                ? ResponseEntity.ok(FavoritoResponseDTO.fromModel(favorito))
                : ResponseEntity.status(404).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<FavoritoResponseDTO> updateFavorito(
            @PathVariable Long id,
            @Valid @RequestBody FavoritoRequestDTO favoritoDetails) {
        FavoritoModel updatedFavorito = favoritoService.update(id, favoritoDetails, usuarioRepository, servicosRepository);
        return updatedFavorito != null
                ? ResponseEntity.ok(FavoritoResponseDTO.fromModel(updatedFavorito))
                : ResponseEntity.status(404).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFavorito(@PathVariable Long id) {
        if (favoritoService.deleteById(id)) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(404).build();
    }
}
