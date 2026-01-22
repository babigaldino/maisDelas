package com.delas.api.service;

import com.delas.api.model.ContratacaoModel;
import com.delas.api.repository.ContratacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ContratacaoService {

    @Autowired
    private ContratacaoRepository contratacaoRepository;

    public ContratacaoModel save(ContratacaoModel contratacao) {
        return contratacaoRepository.save(contratacao);
    }

    public List<ContratacaoModel> findAll() {
        return contratacaoRepository.findAll();
    }

    public Optional<ContratacaoModel> findById(Long id) {
        return contratacaoRepository.findById(id);
    }

   public ContratacaoModel atualizarStatus(Long id, String novoStatus) {
    ContratacaoModel contratacao = contratacaoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Contratação não encontrada"));

    contratacao.setStatus(ContratacaoModel.StatusContratacao.valueOf(novoStatus));
    
    return contratacaoRepository.save(contratacao); // ✅ Adicione o return aqui!
}
    public boolean deleteById(Long id) {
        if (contratacaoRepository.existsById(id)) {
            contratacaoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<ContratacaoModel> findByStatus(String status) {
        return contratacaoRepository.findByStatus(status);
    }

    public List<ContratacaoModel> findByUsuarioId(Long usuarioId) {
        return contratacaoRepository.findByUsuarioId(usuarioId);
    }

}
