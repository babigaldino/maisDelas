package com.delas.api.controller;

import com.delas.api.model.ContratacaoModel;
import com.delas.api.repository.ContratacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contratacao")
public class ContratacaoController {

    @Autowired
    private ContratacaoRepository contratacaoRepository;

    // Criar uma contratação
    @PostMapping
    public ContratacaoModel createContratacao(@RequestBody ContratacaoModel contratacao) {
        return contratacaoRepository.save(contratacao);
    }

    // Listar todas as contratações
    @GetMapping
    public List<ContratacaoModel> getAllContratacoes() {
        return contratacaoRepository.findAll();
    }

    // Buscar contratação por ID
    @GetMapping("/{id}")
    public ContratacaoModel getContratacaoById(@PathVariable Long id) {
        return contratacaoRepository.findById(id).orElse(null);
    }

    // Atualizar uma contratação
    @PutMapping("/{id}")
    public ContratacaoModel updateContratacao(@PathVariable Long id, @RequestBody ContratacaoModel contratacaoDetails) {
        ContratacaoModel contratacao = contratacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contratação não encontrada"));

        // Atualize apenas os campos não nulos da requisição
        if (contratacaoDetails.getComentarios() != null) {
            contratacao.setComentarios(contratacaoDetails.getComentarios());
        }
        if (contratacaoDetails.getDataContratacao() != null) {
            contratacao.setDataContratacao(contratacaoDetails.getDataContratacao());
        }
        if (contratacaoDetails.getUsuario() != null) {
            contratacao.setUsuario(contratacaoDetails.getUsuario());
        }
        if (contratacaoDetails.getServicos() != null) {
            contratacao.setServicos(contratacaoDetails.getServicos());
        }
        if (contratacaoDetails.getStatus() != null) {
            contratacao.setStatus(contratacaoDetails.getStatus());
        }

        // Corrigindo: Acessando o idservicos corretamente
        if (contratacaoDetails.getServicos() != null && contratacaoDetails.getServicos().getIdservicos() != null) {
            contratacao.getServicos().setIdservicos(contratacaoDetails.getServicos().getIdservicos());
        }

        return contratacaoRepository.save(contratacao);
    }

    // Deletar uma contratação
    @DeleteMapping("/{id}")
    public void deleteContratacao(@PathVariable Long id) {
        contratacaoRepository.deleteById(id);
    }
}
