package com.delas.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "avaliacao", indexes = {
        @Index(name = "idx_avaliacao_contratacao_id", columnList = "idcontratacao")
})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AvaliacaoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idavaliacao;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idcontratacao", nullable = false)
    private ContratacaoModel contratacao;

    @NotNull(message = "A nota é obrigatória.")
    @Column(name = "nota", nullable = false, precision = 3, scale = 2)
    @DecimalMin(value = "1.0", message = "A nota deve ser no mínimo 1.")
    @DecimalMax(value = "5.0", message = "A nota deve ser no máximo 5.")
    private BigDecimal nota;

    @Column(name = "comentario", columnDefinition = "TEXT")
    private String comentario;

    @Column(name = "data_avaliacao", nullable = false, updatable = false)
    private LocalDateTime dataAvaliacao;

    @PrePersist
    protected void onCreate() {
        if (this.dataAvaliacao == null) {
            this.dataAvaliacao = LocalDateTime.now();
        }
    }
}
