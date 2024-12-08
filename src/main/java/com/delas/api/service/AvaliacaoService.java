package com.delas.api.service;


import com.delas.api.model.AvaliacaoModel;
import com.delas.api.repository.AvaliacaoRepository;
import com.delas.api.repository.ContratacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Service
public class AvaliacaoService {

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    @Autowired
    private ContratacaoRepository contratacaoRepository;


    @PostMapping("/avaliacao")
    public ResponseEntity<AvaliacaoModel> createAvaliacao(@RequestBody AvaliacaoModel avaliacao) {
        try {
            AvaliacaoModel savedAvaliacao = avaliacaoRepository.save(avaliacao);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedAvaliacao);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
