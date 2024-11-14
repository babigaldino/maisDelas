package com.delas.api.service;

import com.delas.api.repository.FavoritoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FavoritoService {
    @Autowired private FavoritoRepository favoritoRepository;
}
