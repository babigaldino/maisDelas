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
        ContratacaoModel contratacao = contratacaoRepository.findById(id).orElse(null);
        if (contratacao != null) {
            contratacao.setComentarios(contratacaoDetails.getCampoExemplo());
            return contratacaoRepository.save(contratacao);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteContratacao(@PathVariable Long id) {
        contratacaoRepository.deleteById(id);
    }
}


//
