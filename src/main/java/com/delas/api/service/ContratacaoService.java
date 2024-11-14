package com.delas.api.service;

import com.delas.api.repository.ContratacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContratacaoService {
    @Autowired private ContratacaoRepository contratacaoRepository;
}
