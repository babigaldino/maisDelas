package com.delas.api.repository;

import com.delas.api.model.ContratacaoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContratacaoRepository extends JpaRepository<ContratacaoModel, Long> {


    List<ContratacaoModel> findByStatus(String status);

    List<ContratacaoModel> findByUsuarioId(Long usuarioId);

     @Query("SELECT c FROM ContratacaoModel c WHERE c.servico.usuario.id = :prestadoraId ORDER BY c.dataContratacao DESC")
    List<ContratacaoModel> findByPrestadoraId(@Param("prestadoraId") Long prestadoraId);
}


