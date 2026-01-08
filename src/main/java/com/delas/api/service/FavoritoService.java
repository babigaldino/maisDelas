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

    public FavoritoModel save(FavoritoModel favorito) {
        return favoritoRepository.save(favorito);
    }

    public List<FavoritoModel> findAll() {
        return favoritoRepository.findAll();
    }

    public FavoritoModel findById(Long id) {
        return favoritoRepository.findById(id).orElse(null);
    }

    // ✅ CORRIGIDO: Aceita FavoritoRequestDTO e faz update real
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
