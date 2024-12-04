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

    @PostMapping
    public ContratacaoModel createContratacao(@RequestBody ContratacaoModel contratacao) {
        return contratacaoRepository.save(contratacao);
    }

    @GetMapping
    public List<ContratacaoModel> getAllContratacoes() {
        return contratacaoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ContratacaoModel getContratacaoById(@PathVariable Long id) {
        return contratacaoRepository.findById(id).orElse(null);
    }

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
        if (contratacaoDetails.getId() != null) {
            contratacao.setId(contratacaoDetails.getId());
        }
        if (contratacaoDetails.getIdservicos() != null) {
            contratacao.setIdservicos(contratacaoDetails.getIdservicos());
        }
        if (contratacaoDetails.getStatus() != null) {
            contratacao.setStatus(contratacaoDetails.getStatus());
        }
        // Corrigindo: Acessando o idservicos corretamente
        if (contratacaoDetails.getIdservicos() != null && contratacaoDetails.getIdservicos().getIdservicos() != null) {
            contratacao.getIdservicos().setIdservicos(contratacaoDetails.getIdservicos().getIdservicos());
        }


        return contratacaoRepository.save(contratacao);
    }


    @DeleteMapping("/{id}")
    public void deleteContratacao(@PathVariable Long id) {
        contratacaoRepository.deleteById(id);
    }
}


