package com.delas.api.service;

import com.delas.api.dto.FavoritoRequestDTO;
import com.delas.api.model.FavoritoModel;
import com.delas.api.model.ServicosModel;
import com.delas.api.model.UsuarioModel;
import com.delas.api.repository.FavoritoRepository;
import com.delas.api.repository.ServicosRepository;
import com.delas.api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class FavoritoService {

    @Autowired
    private FavoritoRepository favoritoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ServicosRepository servicosRepository;

    public List<UsuarioModel> findPrestadorasFavoritas(Long usuarioId) {
        return favoritoRepository.findPrestadorasFavoritasByUsuarioId(usuarioId);
    }

    public FavoritoModel addFavorito(Long usuarioId, Long servicoId) {
        // Verifica se já existe
        if (favoritoRepository.existsByUsuarioFavoritoIdAndServicoFavoritoId(usuarioId, servicoId)) {
            throw new RuntimeException("Serviço já está nos favoritos");
        }
        
        UsuarioModel usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        
        ServicosModel servico = servicosRepository.findById(servicoId)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));
        
        FavoritoModel favorito = new FavoritoModel();
        favorito.setUsuarioFavorito(usuario);
        favorito.setServicoFavorito(servico);
        
        return favoritoRepository.save(favorito);
    }

    public void removeFavorito(Long usuarioId, Long servicoId) {
        favoritoRepository.deleteByUsuarioFavoritoIdAndServicoFavoritoId(usuarioId, servicoId);
    }

    public boolean isFavorito(Long usuarioId, Long servicoId) {
        return favoritoRepository.existsByUsuarioFavoritoIdAndServicoFavoritoId(usuarioId, servicoId);
    }

    public FavoritoModel save(FavoritoModel favorito) {
        return favoritoRepository.save(favorito);
    }

    public List<FavoritoModel> findAll() {
        return favoritoRepository.findAll();
    }
    
    public List<FavoritoModel> findByUsuarioId(Long usuarioId) {
        return favoritoRepository.findByUsuarioFavoritoId(usuarioId);
    }

    public FavoritoModel findById(Long id) {
        return favoritoRepository.findById(id).orElse(null);
    }

    public FavoritoModel update(Long id, FavoritoRequestDTO favoritoDTO,
                               UsuarioRepository usuarioRepository,
                               ServicosRepository servicosRepository) {
        FavoritoModel favorito = favoritoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Favorito não encontrado"));

        if (favoritoDTO.getUsuarioId() != null) {
            UsuarioModel usuario = usuarioRepository.findById(favoritoDTO.getUsuarioId())
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
            favorito.setUsuarioFavorito(usuario);
        }

        if (favoritoDTO.getServicoId() != null) {
            ServicosModel servico = servicosRepository.findById(favoritoDTO.getServicoId())
                    .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));
            favorito.setServicoFavorito(servico);
        }

        return favoritoRepository.save(favorito);
    }

    public boolean deleteById(Long id) {
        if (favoritoRepository.existsById(id)) {
            favoritoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
