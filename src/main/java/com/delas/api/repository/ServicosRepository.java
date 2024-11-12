package com.example.api_delas.repository;
import com.example.api_delas.model.ServicosModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicosRepository extends JpaRepository<ServicosModel, Long> {
}
