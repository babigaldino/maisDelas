package com.delas.api.controller;

import com.delas.api.model.FavoritoModel;
import com.delas.api.service.FavoritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/favorito")
public class FavoritoController {

    @Autowired
    private FavoritoService favoritoService;

    // criar  um novo favorito
    @PostMapping
    public ResponseEntity<FavoritoModel> createFavorito(@RequestBody FavoritoModel favorito) {
        FavoritoModel novoFavorito = favoritoService.save(favorito);
        return ResponseEntity.ok(novoFavorito);
    }

    // pegar todos os favoritos
    @GetMapping
    public List<FavoritoModel> getAllFavoritos() {
        return favoritoService.findAll();
    }

    // pegar um favorito pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<FavoritoModel> getFavoritoById(@PathVariable Long id) {
        FavoritoModel favorito = favoritoService.findById(id);
        return favorito != null ? ResponseEntity.ok(favorito) : ResponseEntity.notFound().build();
    }

    // atualizar um favorito existente
    @PutMapping("/{id}")
    public ResponseEntity<FavoritoModel> updateFavorito(@PathVariable Long id, @RequestBody FavoritoModel favoritoDetails) {
        FavoritoModel updatedFavorito = favoritoService.update(id, favoritoDetails);
        return updatedFavorito != null ? ResponseEntity.ok(updatedFavorito) : ResponseEntity.notFound().build();
    }

    // excluir um favorito pelo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFavorito(@PathVariable Long id) {
        if (favoritoService.deleteById(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
