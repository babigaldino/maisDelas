package com.delas.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Table(name = "favorito", indexes = {
    @Index(name = "idx_favorito_usuario_id", columnList = "idprestadorfavorito"),
    @Index(name = "idx_favorito_servico_id", columnList = "idservicofavorito")
})
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FavoritoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFavorito;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idservicofavorito", nullable = false)
    private ServicosModel servicoFavorito;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idprestadorfavorito", nullable = false)
    private UsuarioModel usuarioFavorito;

    @Column(name = "data_favoritamento", nullable = false, updatable = false)
    private LocalDateTime dataFavoritamento = LocalDateTime.now();

    @PrePersist
    protected void onCreate() {
        if (this.dataFavoritamento == null) {
            this.dataFavoritamento = LocalDateTime.now();
        }
    }
}
