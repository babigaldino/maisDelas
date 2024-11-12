package com.example.api_delas.service;


import com.example.api_delas.repository.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuariosService {
    @Autowired
    private UsuariosRepository usuariosRepository;
}
