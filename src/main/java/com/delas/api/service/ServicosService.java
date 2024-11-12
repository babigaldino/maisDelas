package com.example.api_delas.service;

import com.example.api_delas.repository.ServicosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicosService {
    @Autowired
    private ServicosRepository servicosRepository;
}
