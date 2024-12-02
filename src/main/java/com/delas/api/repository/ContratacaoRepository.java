package com.delas.api.repository;

import com.delas.api.model.ContratacaoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContratacaoRepository extends JpaRepository<ContratacaoModel, Long> {

    // Corrigido: Usando o relacionamento correto entre ContratacaoModel e ServicosModel
    List<ContratacaoModel> findByServicos_Idservicos(Long id);

    // Outro exemplo: caso a propriedade do relacionamento seja diferente
    // List<ContratacaoModel> findByServicosIdservicos(Long servicosId);

    List<ContratacaoModel> findByStatus(String status);
}
