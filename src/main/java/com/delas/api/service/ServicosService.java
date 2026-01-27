package com.delas.api.service;

import com.delas.api.dto.ServicosRequestDTO;
import com.delas.api.model.ServicosModel;
import com.delas.api.model.UsuarioModel;
import com.delas.api.repository.ServicosRepository;
import com.delas.api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class ServicosService {

    @Autowired
    private ServicosRepository servicosRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public ServicosModel save(ServicosRequestDTO servicoDTO, Long usuarioId) {
        UsuarioModel usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!usuario.getTipo().equals(UsuarioModel.TipoUsuario.PRESTADOR)) {
            throw new RuntimeException("Apenas usuários do tipo PRESTADOR podem adicionar serviços.");
        }

        ServicosModel servico = new ServicosModel();
        servico.setDescricao(servicoDTO.getDescricao());
        servico.setPreco(servicoDTO.getPreco());
        servico.setTitulo(servicoDTO.getTitulo());
        servico.setCategoria(servicoDTO.getCategoria());
        servico.setUsuario(usuario);

        return servicosRepository.save(servico);
    }

    public ServicosModel update(Long id, ServicosRequestDTO servicoDTO) {
        ServicosModel servico = servicosRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));

        servico.setDescricao(servicoDTO.getDescricao());
        servico.setPreco(servicoDTO.getPreco());
        servico.setTitulo(servicoDTO.getTitulo());
        servico.setCategoria(servicoDTO.getCategoria());

        return servicosRepository.save(servico);
    }

    public List<ServicosModel> findAll() {
        return servicosRepository.findAll();
    }

    public List<ServicosModel> findAllOrderByNota(boolean ascending) {
        return ascending
                ? servicosRepository.findAllByOrderByNotaAsc()
                : servicosRepository.findAllByOrderByNotaDesc();
    }

    public ServicosModel findById(Long id) {
        return servicosRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado com id: " + id));
    }

    public List<ServicosModel> findByDataCriacaoAfter(LocalDateTime dataInicial) {
        return servicosRepository.findByDatacriacaoAfter(dataInicial);
    }

    public List<ServicosModel> buscarServicosPorPreco(BigDecimal precoMinimo, BigDecimal precoMaximo) {
        return servicosRepository.findByPrecoBetween(precoMinimo, precoMaximo);
    }

    public List<ServicosModel> findByCategoriaContaining(String categoria) {
        return servicosRepository.findByCategoriaContainingIgnoreCase(categoria);
    }

    public boolean deleteById(Long id) {
        if (!servicosRepository.existsById(id)) return false;
        servicosRepository.deleteById(id);
        return true;
    }

   
    public ServicosModel updateNota(Long id, BigDecimal nota) {
        if (nota == null) {
            throw new IllegalArgumentException("Nota é obrigatória");
        }

        if (nota.compareTo(BigDecimal.ZERO) < 0 || nota.compareTo(new BigDecimal("5")) > 0) {
            throw new IllegalArgumentException("Nota deve estar entre 0 e 5");
        }

        ServicosModel servico = findById(id);
        servico.setNota(nota);
        return servicosRepository.save(servico);
    }
}
