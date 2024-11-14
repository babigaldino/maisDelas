package com.delas.api.repository;
import com.delas.api.model.ContratacaoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContratacaoRepository extends JpaRepository<ContratacaoModel, Long> {

    List<ContratacaoModel> findByIdUsuarioId(Integer usuarioId);
    List<ContratacaoModel> findByIdservicosIdservicos(Long servicosId);
    List<ContratacaoModel> findByStatus(String status);

}
