package com.example.api_delas.service;

import com.example.api_delas.repository.FavoritoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FavoritoService {
    @Autowired private FavoritoRepository favoritoRepository;
}
