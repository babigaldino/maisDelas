package com.delas.api.service;

import com.delas.api.dto.AvaliacaoRequestDTO;
import com.delas.api.model.AvaliacaoModel;
import com.delas.api.model.ContratacaoModel;
import com.delas.api.model.ServicosModel;
import com.delas.api.model.UsuarioModel;
import com.delas.api.repository.AvaliacaoRepository;
import com.delas.api.repository.ContratacaoRepository;
import com.delas.api.repository.ServicosRepository;
import com.delas.api.repository.UsuarioRepository;
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
    
    @Autowired
    private ServicosRepository servicoRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    public AvaliacaoModel save(AvaliacaoRequestDTO avaliacaoDTO) {
        ContratacaoModel contratacao = contratacaoRepository.findById(avaliacaoDTO.getContratacaoId())
                .orElseThrow(() -> new RuntimeException("Contratação não encontrada"));

        AvaliacaoModel avaliacao = new AvaliacaoModel();
        avaliacao.setContratacao(contratacao);
        avaliacao.setNota(avaliacaoDTO.getNota());
        avaliacao.setComentario(avaliacaoDTO.getComentario());

        AvaliacaoModel savedAvaliacao = avaliacaoRepository.save(avaliacao);
        
        //  ATUALIZA AS MÉDIAS
        Long servicoId = contratacao.getServico().getIdservicos();
        Long prestadoraId = contratacao.getServico().getUsuario().getId();
        
        atualizarMediaServico(servicoId);
        atualizarMediaPrestadora(prestadoraId);
        
        return savedAvaliacao;
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

        AvaliacaoModel updatedAvaliacao = avaliacaoRepository.save(avaliacao);
        
        //  ATUALIZA AS MÉDIAS
        Long servicoId = avaliacao.getContratacao().getServico().getIdservicos();
        Long prestadoraId = avaliacao.getContratacao().getServico().getUsuario().getId();
        
        atualizarMediaServico(servicoId);
        atualizarMediaPrestadora(prestadoraId);
        
        return updatedAvaliacao;
    }

    public boolean deleteById(Long id) {
        if (avaliacaoRepository.existsById(id)) {
            AvaliacaoModel avaliacao = findById(id);
            Long servicoId = avaliacao.getContratacao().getServico().getIdservicos();
            Long prestadoraId = avaliacao.getContratacao().getServico().getUsuario().getId();
            
            avaliacaoRepository.deleteById(id);
            
            //  ATUALIZA AS MÉDIAS
            atualizarMediaServico(servicoId);
            atualizarMediaPrestadora(prestadoraId);
            
            return true;
        }
        return false;
    }
    
    
    @Transactional
    public void atualizarMediaServico(Long servicoId) {
        List<AvaliacaoModel> avaliacoes = avaliacaoRepository.findByContratacao_Servico_Idservicos(servicoId);
        
        ServicosModel servico = servicoRepository.findById(servicoId)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));
        
        if (!avaliacoes.isEmpty()) {
           
            double media = avaliacoes.stream()
                    .filter(a -> a.getNota() != null) // Ignora notas null
                    .mapToDouble(AvaliacaoModel::getNota) // ← CORRIGIDO
                    .average()
                    .orElse(0.0);
            
            servico.setNota(media);
        } else {
            servico.setNota(0.0);
        }
        
        servicoRepository.save(servico);
    }
    
    @Transactional
    public void atualizarMediaPrestadora(Long prestadoraId) {
        List<ServicosModel> servicos = servicoRepository.findByUsuarioId(prestadoraId);
        
        UsuarioModel prestadora = usuarioRepository.findById(prestadoraId)
                .orElseThrow(() -> new RuntimeException("Prestadora não encontrada"));
        
        if (!servicos.isEmpty()) {
            double mediaPrestadora = servicos.stream()
                    .filter(s -> s.getNota() != null && s.getNota() > 0)
                    .mapToDouble(ServicosModel::getNota)
                    .average()
                    .orElse(0.0);
            
            prestadora.setNota(mediaPrestadora);
        } else {
            prestadora.setNota(0.0);
        }
        
        usuarioRepository.save(prestadora);
    }
}
