package com.delas.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "servicos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ServicosModel {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long idservicos; // Alteração no nome do campo ID

        @Column(columnDefinition = "TEXT", nullable = false)
        @NotBlank(message = "A descrição é obrigatória.")
        private String descricao;

        @Column(precision = 10, scale = 2, nullable = false)
        @DecimalMin(value = "0.0", inclusive = false, message = "O preço deve ser maior que zero.") // Alteração na validação do preço
        private BigDecimal preco;

        @Column(length = 70, nullable = false)
        @NotBlank(message = "O título é obrigatório.")
        private String titulo;

        @Column(name = "datacriacao_servico", nullable = false, updatable = false) // Alteração no nome da coluna
        private LocalDateTime datacriacao;

        @Column(length = 100)
        private String categoria;

        @Column(name = "idfavorito") // Alteração no nome da coluna
        private Long idfavorito;

        @Column(nullable = false)
        @DecimalMin(value = "1.0", message = "A nota deve ser no mínimo 1.")
        @DecimalMax(value = "5.0", message = "A nota deve ser no máximo 5.")
        private Double nota;

        // Método para definir o valor de datacriacao automaticamente
        @PrePersist
        protected void onCreate() {
                // Verifica se a data não está definida para evitar sobrescrever um valor já existente
                if (this.datacriacao == null) {
                        this.datacriacao = LocalDateTime.now();
                }
        }
}
