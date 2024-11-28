package com.delas.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "avaliacao")
public class AvaliacaoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idavaliacao;

    @OneToOne
    @JoinColumn(name = "idcontratacao", nullable = false)
    private ContratacaoModel idcontratacao;

    @Column(name = "nota", nullable = false)
    private int nota;

    @Column(name = "comentario")
    private String comentario;

    @Column(name = "data")
    @Temporal(TemporalType.TIMESTAMP)
    private Date data;

    // Construtor específico para simplificar nos testes
    public AvaliacaoModel(Long idavaliacao, ContratacaoModel idcontratacao, int nota) {
        this.idavaliacao = idavaliacao;
        this.idcontratacao = idcontratacao;
        this.nota = nota;
    }
}
