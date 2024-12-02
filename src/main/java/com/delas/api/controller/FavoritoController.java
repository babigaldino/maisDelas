package com.delas.api.controller;

import com.delas.api.model.FavoritoModel;
import com.delas.api.model.ServicosModel;
import com.delas.api.model.UsuarioModel;
import com.delas.api.repository.ServicosRepository;
import com.delas.api.repository.UsuarioRepository;
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

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ServicosRepository servicosRepository;

    // Criar um novo favorito
    @PostMapping
    public ResponseEntity<FavoritoModel> createFavorito(@RequestBody FavoritoModel favorito) {
        // Valida se os IDs estão presentes
        if (favorito.getUsuarioFavorito() == null || favorito.getUsuarioFavorito().getId() == null) {
            throw new IllegalArgumentException("ID do usuário é obrigatório.");
        }
        // A diferença aqui: no segundo código, o ID do serviço é acessado com 'getIdservicos()'
        if (favorito.getServicoFavorito() == null || favorito.getServicoFavorito().getIdservicos() == null) {
            throw new IllegalArgumentException("ID do serviço é obrigatório.");
        }

        // Busca os registros no banco
        UsuarioModel usuario = usuarioRepository.findById(favorito.getUsuarioFavorito().getId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // A diferença aqui: no segundo código, o ID do serviço é acessado com 'getIdservicos()'
        ServicosModel servico = servicosRepository.findById(favorito.getServicoFavorito().getIdservicos())
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));

        // Associa as entidades ao favorito
        favorito.setUsuarioFavorito(usuario);
        favorito.setServicoFavorito(servico);

        // Salva no banco
        FavoritoModel novoFavorito = favoritoService.save(favorito);
        return ResponseEntity.ok(novoFavorito);
    }

    // Pegar todos os favoritos
    @GetMapping
    public List<FavoritoModel> getAllFavoritos() {
        return favoritoService.findAll();
    }

    // Pegar um favorito pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<FavoritoModel> getFavoritoById(@PathVariable Long id) {
        FavoritoModel favorito = favoritoService.findById(id);
        return favorito != null ? ResponseEntity.ok(favorito) : ResponseEntity.notFound().build();
    }

    // Atualizar um favorito existente
    @PutMapping("/{id}")
    public ResponseEntity<FavoritoModel> updateFavorito(@PathVariable Long id, @RequestBody FavoritoModel favoritoDetails) {
        FavoritoModel updatedFavorito = favoritoService.update(id, favoritoDetails);
        return updatedFavorito != null ? ResponseEntity.ok(updatedFavorito) : ResponseEntity.notFound().build();
    }

    // Excluir um favorito pelo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFavorito(@PathVariable Long id) {
        if (favoritoService.deleteById(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
