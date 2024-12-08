package com.delas.api.service;

import com.delas.api.model.ServicosModel;
import com.delas.api.model.UsuarioModel;
import com.delas.api.repository.ServicosRepository;
import com.delas.api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ServicosService {

    @Autowired
    private ServicosRepository servicosRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Método para salvar um novo serviço associado a um usuário do tipo PRESTADOR
    public ServicosModel save(ServicosModel servico, Long usuarioId) {
        // Verifica se o usuário existe
        UsuarioModel usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Verifica se o usuário é do tipo PRESTADOR
        if (!usuario.getTipo().equals(UsuarioModel.TipoUsuario.PRESTADOR)) {
            throw new RuntimeException("Apenas usuários do tipo PRESTADOR podem adicionar serviços.");
        }

        // Relaciona o serviço ao usuário prestador
        servico.setUsuario(usuario);

        return servicosRepository.save(servico);
    }

    public List<ServicosModel> buscarServicosPorPreco(BigDecimal precoMinimo, BigDecimal precoMaximo) {
        return servicosRepository.findByPrecoBetween(precoMinimo, precoMaximo);
    }

    // Método para buscar serviços por categoria (com suporte a busca parcial)
    public List<ServicosModel> findByCategoriaContaining(String categoria) {
        return servicosRepository.findByCategoriaContainingIgnoreCase(categoria);
    }

    // Método para listar todos os serviços
    public List<ServicosModel> findAll() {
        return servicosRepository.findAll();
    }

    // Método para listar serviços ordenados por nota
    public List<ServicosModel> findAllOrderByNota(boolean ascending) {
        // Ordenação pela "nota" em ordem crescente ou decrescente
        Sort sort = ascending ? Sort.by("nota").ascending() : Sort.by("nota").descending();
        return servicosRepository.findAll(sort);
    }

    // Método para encontrar um serviço pelo ID
    public ServicosModel findById(Long id) {
        // Retorna o serviço ou lança exceção se não encontrado
        return servicosRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado com id: " + id));
    }



    // Método para buscar serviços criados após uma data
    public List<ServicosModel> findByDataCriacaoAfter(LocalDateTime dataInicial) {
        return servicosRepository.findByDatacriacaoAfter(dataInicial);
    }

    // Método para buscar serviços por palavra-chave no título
    public List<ServicosModel> findByTituloContaining(String palavraChave) {
        return servicosRepository.findByTituloContainingIgnoreCase(palavraChave);
    }

    // Método para atualizar um serviço existente
    public ServicosModel update(Long id, ServicosModel servicoDetails) {
        // Verifica se o serviço existe, caso contrário lança uma exceção
        ServicosModel servico = findById(id);
        servico.setDescricao(servicoDetails.getDescricao());
        servico.setPreco(servicoDetails.getPreco());
        servico.setTitulo(servicoDetails.getTitulo());
        servico.setDatacriacao(servicoDetails.getDatacriacao());  // Corrigido para 'setDatacriacao'
        servico.setCategoria(servicoDetails.getCategoria());
        servico.setNota(servicoDetails.getNota()); // Atualiza a nota
        return servicosRepository.save(servico);
    }

    // Método para atualizar apenas a nota de um serviço
    public ServicosModel updateNota(Long id, Double nota) {
        // Verifica se o serviço existe
        ServicosModel servico = findById(id);
        if (nota < 0 || nota > 10) {
            throw new IllegalArgumentException("Nota deve estar entre 0 e 10");
        }
        servico.setNota(nota);
        return servicosRepository.save(servico);
    }

    // Método para deletar um serviço pelo ID
    public boolean deleteById(Long id) {
        // Verifica se o serviço existe
        if (servicosRepository.existsById(id)) {
            servicosRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
