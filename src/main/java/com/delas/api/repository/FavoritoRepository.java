package com.delas.api.repository;

import com.delas.api.model.FavoritoModel;
import com.delas.api.model.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoritoRepository extends JpaRepository<FavoritoModel, Long> {
    
    List<FavoritoModel> findByUsuarioFavoritoId(Long usuarioId);
    
    @Query("SELECT DISTINCT f.servicoFavorito.usuario FROM FavoritoModel f WHERE f.usuarioFavorito.id = :usuarioId")
    List<UsuarioModel> findPrestadorasFavoritasByUsuarioId(@Param("usuarioId") Long usuarioId);
    
    @Query("SELECT CASE WHEN COUNT(f) > 0 THEN true ELSE false END FROM FavoritoModel f WHERE f.usuarioFavorito.id = :usuarioId AND f.servicoFavorito.id = :servicoId")
    boolean existsByUsuarioFavoritoIdAndServicoFavoritoId(@Param("usuarioId") Long usuarioId, @Param("servicoId") Long servicoId);
    
    @Transactional
    @Modifying
    @Query("DELETE FROM FavoritoModel f WHERE f.usuarioFavorito.id = :usuarioId AND f.servicoFavorito.id = :servicoId")
    void deleteByUsuarioFavoritoIdAndServicoFavoritoId(@Param("usuarioId") Long usuarioId, @Param("servicoId") Long servicoId);
    
    @Query("SELECT f FROM FavoritoModel f WHERE f.usuarioFavorito.id = :usuarioId AND f.servicoFavorito.id = :servicoId")
    Optional<FavoritoModel> findByUsuarioFavoritoIdAndServicoFavoritoId(@Param("usuarioId") Long usuarioId, @Param("servicoId") Long servicoId);
}
