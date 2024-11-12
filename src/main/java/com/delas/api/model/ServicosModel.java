package com.example.api_delas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Table (name = "servicos")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ServicosModel {


        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long idservicos;

        @Column(columnDefinition = "TEXT")
        private String descricao;

        @Column(precision = 10, scale = 2)
        private BigDecimal preco;

        @Column(length = 70, nullable = false)
        private String titulo;

        @Column(name = "datacriacao_servico", nullable = false)
        private LocalDateTime datacriacao;

        @Column(length = 100)
        private String categoria;

        @Column(name = "favorito_idfavorito")
        private Long favoritoId;



}


