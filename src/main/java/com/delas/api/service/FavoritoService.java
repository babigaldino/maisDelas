package com.delas.api.service;

import com.delas.api.model.FavoritoModel;
import com.delas.api.repository.FavoritoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FavoritoService {

    private final FavoritoRepository favoritoRepository;

    @Autowired
    public FavoritoService(FavoritoRepository favoritoRepository) {
        this.favoritoRepository = favoritoRepository;
    }

    // Método para salvar um novo favorito
    public FavoritoModel save(FavoritoModel favorito) {
        return favoritoRepository.save(favorito);
    }

    // Método para listar todos os favoritos
    public List<FavoritoModel> findAll() {
        return favoritoRepository.findAll();
    }

    // Método para buscar um favorito pelo ID
    public FavoritoModel findById(Long id) {
        return favoritoRepository.findById(id).orElse(null);
    }

    // Método para atualizar um favorito existente
    public FavoritoModel update(Long id, FavoritoModel favoritoDetails) {
        Optional<FavoritoModel> favoritoOptional = favoritoRepository.findById(id);
        if (favoritoOptional.isPresent()) {
            FavoritoModel favorito = favoritoOptional.get();
            return favoritoRepository.save(favorito);
        }
        return null;
    }

    // Método para deletar um favorito pelo ID
    public boolean deleteById(Long id) {
        if (favoritoRepository.existsById(id)) {
            favoritoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
