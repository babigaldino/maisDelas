package com.delas.api.service;

import com.delas.api.dto.AvaliacaoDTO;
import com.delas.api.model.AvaliacaoModel;
import com.delas.api.model.ContratacaoModel;
import com.delas.api.repository.AvaliacaoRepository;
import com.delas.api.repository.ContratacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AvaliacaoService {

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    @Autowired
    private ContratacaoRepository contratacaoRepository;

    // Função para converter o modelo de avaliação para DTO
    public AvaliacaoDTO converterParaDTO(AvaliacaoModel avaliacao) {
        // Dados do banco
        int nota = avaliacao.getNota();

        // Dados gerados dinamicamente
        String comentario = "Comentário gerado para a avaliação " + avaliacao.getIdavaliacao();
        String nomeCliente = "Cliente padrão"; // Pode ser fixo ou buscado de outro local
        LocalDate dataAvaliacao = LocalDate.now(); // Atribuindo a data atual

        return new AvaliacaoDTO(nota, comentario, nomeCliente, dataAvaliacao);
    }

    // Salvar uma nova avaliação
    public AvaliacaoModel save(AvaliacaoModel avaliacao) {
        return avaliacaoRepository.save(avaliacao);
    }

    // Método para listar todas as avaliações
    public List<AvaliacaoModel> findAll() {
        return avaliacaoRepository.findAll();
    }

    // Método para encontrar uma avaliação pelo ID
    public Optional<AvaliacaoModel> findById(Long id) {
        return avaliacaoRepository.findById(id);
    }

    // Registrar uma avaliação vinculada a uma contratação
    public AvaliacaoModel registrarAvaliacao(Long contratacaoId, int nota) {
        // Valida se a nota está dentro do intervalo permitido
        if (nota < 1 || nota > 5) {
            throw new IllegalArgumentException("A nota deve estar entre 1 e 5.");
        }

        // Busca a contratação pelo ID
        ContratacaoModel contratacao = contratacaoRepository.findById(contratacaoId)
                .orElseThrow(() -> new RuntimeException("Contratação não encontrada com o ID: " + contratacaoId));

        // Cria nova avaliação
        AvaliacaoModel avaliacao = new AvaliacaoModel();
        avaliacao.setIdcontratacao(contratacao);  // Corrigido: setContratacao()
        avaliacao.setNota(nota);

        // Salvar avaliação no banco de dados
        return avaliacaoRepository.save(avaliacao);
    }
}