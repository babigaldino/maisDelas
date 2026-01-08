package com.delas.api.repository;

import com.delas.api.model.TokenRedefinicaoSenhaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRedefinicaoSenhaRepository extends JpaRepository<TokenRedefinicaoSenhaModel, Long> {

    Optional<TokenRedefinicaoSenhaModel> findByToken(String token);
    void deleteByToken(String token);
}
