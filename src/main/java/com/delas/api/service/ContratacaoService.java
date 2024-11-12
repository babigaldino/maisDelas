package com.example.api_delas.service;

import com.example.api_delas.repository.ContratacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContratacaoService {
    @Autowired private ContratacaoRepository contratacaoRepository;
}
