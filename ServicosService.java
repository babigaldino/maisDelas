package com.delas.api.service;

import com.delas.api.model.ServicosModel;
import com.delas.api.repository.ServicosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServicosService {

    @Autowired
    private ServicosRepository servicosRepository;

    // Método para salvar um novo serviço
    public ServicosModel save(ServicosModel servico) {
        return servicosRepository.save(servico);
    }

    // Método para listar todos os serviços
    public List<ServicosModel> findAll() {
        return servicosRepository.findAll();
    }

    // Método para encontrar um serviço pelo ID
    public Optional<ServicosModel> findById(Long id) {
        return servicosRepository.findById(id);
    }

    // Método para atualizar um serviço existente
    public Optional<ServicosModel> update(Long id, ServicosModel servicoDetails) {
        Optional<ServicosModel> servicoOptional = servicosRepository.findById(id);
        if (servicoOptional.isPresent()) {
            ServicosModel servico = servicoOptional.get();
            servico.setDescricao(servicoDetails.getDescricao());
            servico.setPreco(servicoDetails.getPreco());
            servico.setTitulo(servicoDetails.getTitulo());
            servico.setDatacriacao(servicoDetails.getDatacriacao());
            servico.setCategoria(servicoDetails.getCategoria());
            servico.setIdfavorito(servicoDetails.getIdfavorito());
            return Optional.of(servicosRepository.save(servico));
        }
        return Optional.empty();
    }

    // Método para deletar um serviço pelo ID
    public boolean deleteById(Long id) {
        if (servicosRepository.existsById(id)) {
            servicosRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Método para buscar serviços por categoria (com suporte a busca parcial)
    public List<ServicosModel> findByCategoriaContaining(String categoria) {
        return servicosRepository.findByCategoriaContainingIgnoreCase(categoria);
    }
}
