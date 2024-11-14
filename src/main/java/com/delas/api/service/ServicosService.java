package com.delas.api.service;

import com.delas.api.model.ServicosModel;
import com.delas.api.repository.ServicosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ServicosService {

    @Autowired
    private ServicosRepository servicosRepository;

    //adicionar novo serviço
    public ServicosModel criarServico(ServicosModel servico) {
        servico.setDatacriacao(LocalDateTime.now());
        return servicosRepository.save(servico);
    }

    //atualizar serviço existente
    public ServicosModel atualizarServico(Long id, ServicosModel servicoAtualizado) {
        Optional<ServicosModel> servicoExistente = servicosRepository.findById(id);

        if (servicoExistente.isPresent()) {
            ServicosModel servico = servicoExistente.get();
            servico.setDescricao(servicoAtualizado.getDescricao());
            servico.setPreco(servicoAtualizado.getPreco());
            servico.setTitulo(servicoAtualizado.getTitulo());
            servico.setCategoria(servicoAtualizado.getCategoria());
            servico.setIdfavorito(servicoAtualizado.getIdfavorito());
            return servicosRepository.save(servico);
        } else {
            return null;
        }
    }

    public List<ServicosModel> buscarTodosServicos() {
        return servicosRepository.findAll();
    }

    //busca por id
    public ServicosModel buscarServicoPorId(Long id) {
        return servicosRepository.findById(id).orElse(null);
    }

    //excluir um serviço
    public void deletarServico(Long id) {
        servicosRepository.deleteById(id);
    }

   //busca do serviço por categoria
    public List<ServicosModel> buscaPorCategoria(String categoria) {
        return servicosRepository.findByCategoriaIgnoreCase(categoria);
    }

    //busca do serviço por preço
    public List<ServicosModel> buscaPorPreco(BigDecimal precoMinimo, BigDecimal precoMaximo) {
        return servicosRepository.findByPrecoBetween(precoMinimo, precoMaximo);
    }

    //busca do serviço pelos adicionados recentemente
    public List<ServicosModel> buscaServicosRecentes(LocalDateTime dataInicial) {
        return servicosRepository.findByDatacriacaoAfter(dataInicial);
    }

    //busca por palavra chave, ex: kit = kit festa
    public List<ServicosModel> buscaPorPalavraChaveNoTitulo(String palavraChave) {
        return servicosRepository.findByTituloContainingIgnoreCase(palavraChave);
    }

    //metodo pra adicionar um serviço como favorito
    public ServicosModel marcarComoFavorito(Long id, boolean favorito) {
        ServicosModel servico = servicosRepository.findById(id).orElse(null);
        if (servico != null) {
            servico.setIdfavorito(favorito ? id : null);
            servicosRepository.save(servico);
        }
        return servico;
    }
}
