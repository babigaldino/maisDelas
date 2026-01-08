package com.delas.api.service;

import com.delas.api.dto.AvaliacaoRequestDTO;
import com.delas.api.model.AvaliacaoModel;
import com.delas.api.model.ContratacaoModel;
import com.delas.api.repository.AvaliacaoRepository;
import com.delas.api.repository.ContratacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AvaliacaoService {

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    @Autowired
    private ContratacaoRepository contratacaoRepository;

    // ✅ CORRIGIDO: Removido @PostMapping (é de Controller!)
    // ✅ CORRIGIDO: Aceita AvaliacaoRequestDTO
    // ✅ CORRIGIDO: Retorna AvaliacaoModel, não ResponseEntity
    public AvaliacaoModel save(AvaliacaoRequestDTO avaliacaoDTO) {
        ContratacaoModel contratacao = contratacaoRepository.findById(avaliacaoDTO.getContratacaoId())
                .orElseThrow(() -> new RuntimeException("Contratação não encontrada"));

        AvaliacaoModel avaliacao = new AvaliacaoModel();
        avaliacao.setContratacao(contratacao);
        avaliacao.setNota(avaliacaoDTO.getNota());
        avaliacao.setComentario(avaliacaoDTO.getComentario());

        return avaliacaoRepository.save(avaliacao);
    }

    public List<AvaliacaoModel> findAll() {
        return avaliacaoRepository.findAll();
    }

    public AvaliacaoModel findById(Long id) {
        return avaliacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Avaliação não encontrada"));
    }

    public AvaliacaoModel update(Long id, AvaliacaoRequestDTO avaliacaoDTO) {
        AvaliacaoModel avaliacao = findById(id);

        if (avaliacaoDTO.getNota() != null) {
            avaliacao.setNota(avaliacaoDTO.getNota());
        }
        if (avaliacaoDTO.getComentario() != null) {
            avaliacao.setComentario(avaliacaoDTO.getComentario());
        }

        return avaliacaoRepository.save(avaliacao);
    }

    public boolean deleteById(Long id) {
        if (avaliacaoRepository.existsById(id)) {
            avaliacaoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
