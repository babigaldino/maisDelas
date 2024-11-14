package com.delas.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Table(name = "favorito")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class FavoritoModel {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long idfavorito;

        @ManyToOne
        @JoinColumn(name = "idservicofavorito", nullable = false)
        private ServicosModel servicoFavorito;

        @ManyToOne
        @JoinColumn(name = "idprestadorfavorito", nullable = false)
        private UsuarioModel usuarioFavorito;

        @Column(name = "datafavoritamento")
        private LocalDateTime datafavoritamento;


    }


