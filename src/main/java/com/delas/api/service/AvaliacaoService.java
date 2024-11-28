package com.delas.api.service;
import com.delas.api.dto.AvaliacaoDTO;
import com.delas.api.model.AvaliacaoModel;
import com.delas.api.repository.AvaliacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AvaliacaoService {
    @Autowired private AvaliacaoRepository avaliacaoRepository;

    public AvaliacaoDTO converterParaDTO(AvaliacaoModel avaliacao) {
        // Dados do banco
        int nota = avaliacao.getNota();

        // Dados gerados dinamicamente
        String comentario = "Comentário gerado para a avaliação " + avaliacao.getIdavaliacao();
        String nomeCliente = "Cliente padrão"; // Pode ser fixo ou buscado de outro local
        LocalDate dataAvaliacao = LocalDate.now(); // Atribuindo a data atual

        return new AvaliacaoDTO(nota, comentario, nomeCliente, dataAvaliacao);
    }


    // salvar uma nova avaliação
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


}
