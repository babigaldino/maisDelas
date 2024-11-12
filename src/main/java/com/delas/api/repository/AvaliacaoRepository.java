package com.example.api_delas.repository;
import com.example.api_delas.model.AvaliacaoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface AvaliacaoRepository extends JpaRepository<AvaliacaoModel, Long> {
}
