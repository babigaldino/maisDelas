package com.delas.api.service;
import com.delas.api.repository.ServicosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicosService {
    @Autowired
    private ServicosRepository servicosRepository;
}
