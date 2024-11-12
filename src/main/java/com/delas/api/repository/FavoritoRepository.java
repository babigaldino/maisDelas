package com.example.api_delas.repository;
import com.example.api_delas.model.FavoritoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoritoRepository extends JpaRepository<FavoritoModel, Long>{
}
