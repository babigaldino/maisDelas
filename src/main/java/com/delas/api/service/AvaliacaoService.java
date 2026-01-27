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

import java.math.BigDecimal;
import java.math.RoundingMode;
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

        if (avaliacaoDTO.getNota() == null) {
            throw new IllegalArgumentException("Nota é obrigatória");
        }

        // Ajuste aqui se quiser 1..5 ao invés de 0..5
        if (avaliacaoDTO.getNota().compareTo(BigDecimal.ZERO) < 0
                || avaliacaoDTO.getNota().compareTo(new BigDecimal("5")) > 0) {
            throw new IllegalArgumentException("Nota deve estar entre 0 e 5");
        }

        AvaliacaoModel avaliacao = new AvaliacaoModel();
        avaliacao.setContratacao(contratacao);
        avaliacao.setNota(avaliacaoDTO.getNota());
        avaliacao.setComentario(avaliacaoDTO.getComentario());

        AvaliacaoModel savedAvaliacao = avaliacaoRepository.save(avaliacao);

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

            if (avaliacaoDTO.getNota().compareTo(BigDecimal.ZERO) < 0
                    || avaliacaoDTO.getNota().compareTo(new BigDecimal("5")) > 0) {
                throw new IllegalArgumentException("Nota deve estar entre 0 e 5");
            }

            avaliacao.setNota(avaliacaoDTO.getNota());
        }

        if (avaliacaoDTO.getComentario() != null) {
            avaliacao.setComentario(avaliacaoDTO.getComentario());
        }

        AvaliacaoModel updatedAvaliacao = avaliacaoRepository.save(avaliacao);

        Long servicoId = avaliacao.getContratacao().getServico().getIdservicos();
        Long prestadoraId = avaliacao.getContratacao().getServico().getUsuario().getId();

        atualizarMediaServico(servicoId);
        atualizarMediaPrestadora(prestadoraId);

        return updatedAvaliacao;
    }

    public boolean deleteById(Long id) {
        if (!avaliacaoRepository.existsById(id)) return false;

        AvaliacaoModel avaliacao = findById(id);
        Long servicoId = avaliacao.getContratacao().getServico().getIdservicos();
        Long prestadoraId = avaliacao.getContratacao().getServico().getUsuario().getId();

        avaliacaoRepository.deleteById(id);

        atualizarMediaServico(servicoId);
        atualizarMediaPrestadora(prestadoraId);

        return true;
    }

    @Transactional
    public void atualizarMediaServico(Long servicoId) {
        List<AvaliacaoModel> avaliacoes =
                avaliacaoRepository.findByContratacao_Servico_Idservicos(servicoId);

        ServicosModel servico = servicoRepository.findById(servicoId)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));

        List<BigDecimal> notas = avaliacoes.stream()
                .map(AvaliacaoModel::getNota)
                .filter(n -> n != null)
                .toList();

        if (!notas.isEmpty()) {
            BigDecimal soma = notas.stream()
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal media = soma.divide(BigDecimal.valueOf(notas.size()), 2, RoundingMode.HALF_UP);
            servico.setNota(media);
        } else {
            servico.setNota(BigDecimal.ZERO);
        }

        servicoRepository.save(servico);
    }

    @Transactional
    public void atualizarMediaPrestadora(Long prestadoraId) {
        List<ServicosModel> servicos = servicoRepository.findByUsuarioId(prestadoraId);

        UsuarioModel prestadora = usuarioRepository.findById(prestadoraId)
                .orElseThrow(() -> new RuntimeException("Prestadora não encontrada"));

        List<BigDecimal> notas = servicos.stream()
                .map(ServicosModel::getNota)
                .filter(n -> n != null && n.compareTo(BigDecimal.ZERO) > 0)
                .toList();

        if (!notas.isEmpty()) {
            BigDecimal soma = notas.stream()
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal mediaPrestadora =
                    soma.divide(BigDecimal.valueOf(notas.size()), 2, RoundingMode.HALF_UP);

            prestadora.setNota(mediaPrestadora);
        } else {
            prestadora.setNota(BigDecimal.ZERO);
        }

        usuarioRepository.save(prestadora);
    }
}
