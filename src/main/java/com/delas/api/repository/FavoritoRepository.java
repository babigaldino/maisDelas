package com.delas.api.repository;
import com.delas.api.model.FavoritoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoritoRepository extends JpaRepository<FavoritoModel, Long>{
}
