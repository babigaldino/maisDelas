package com.delas.api.repository;

import com.delas.api.model.ServicosModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ServicosRepository extends JpaRepository<ServicosModel, Long> {

    // Buscar serviços por categoria, ignorando case
    List<ServicosModel> findByCategoriaIgnoreCase(String categoria);

    // Buscar serviços por faixa de preço
    List<ServicosModel> findByPrecoBetween(BigDecimal precoMinimo, BigDecimal precoMaximo);

    // Buscar serviços criados após uma data específica
    List<ServicosModel> findByDatacriacaoAfter(LocalDateTime dataInicial);

    // Buscar serviços por palavra-chave no título
    List<ServicosModel> findByTituloContainingIgnoreCase(String palavraChave);

    // Buscar todos os serviços ordenados dinamicamente
    List<ServicosModel> findAll(Sort sort);
}
