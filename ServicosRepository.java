package com.delas.api.repository;

import com.delas.api.model.ServicosModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ServicosRepository extends JpaRepository<ServicosModel, Long> {
    List<ServicosModel> findByCategoriaIgnoreCase(String categoria);

    List<ServicosModel> findByPrecoBetween(BigDecimal precoMinimo, BigDecimal precoMaximo);

    List<ServicosModel> findByDatacriacaoAfter(LocalDateTime dataInicial);

    List<ServicosModel> findByTituloContainingIgnoreCase(String palavraChave);

    // Adicionar busca parcial por categoria
    List<ServicosModel> findByCategoriaContainingIgnoreCase(String categoria);

}