package com.delas.api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "servicos", indexes = {
    @Index(name = "idx_servicos_usuario_id", columnList = "usuario_id"),
    @Index(name = "idx_servicos_categoria", columnList = "categoria")
})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ServicosModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idservicos;

    @Column(columnDefinition = "TEXT", nullable = false)
    @NotBlank(message = "A descrição é obrigatória.")
    private String descricao;

    @Column(columnDefinition = "NUMERIC(10,2)", nullable = false)
    @DecimalMin(value = "0.0", inclusive = false, message = "O preço deve ser maior que zero.")
    private BigDecimal preco;

    @Column(length = 70, nullable = false)
    @NotBlank(message = "O título é obrigatório.")
    @Size(min = 5, max = 70, message = "O título deve ter entre 5 e 70 caracteres.")
    private String titulo;

    @Column(name = "data_criacao_servico", nullable = false, updatable = false)
    private LocalDateTime datacriacao = LocalDateTime.now();

    @Column(length = 100, nullable = false)
    @NotBlank(message = "A categoria é obrigatória.")
    private String categoria;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    @JsonBackReference
    private UsuarioModel usuario;

    @Column(name = "nota", precision = 3, scale = 2)
    private BigDecimal nota = BigDecimal.ZERO;


    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
        name = "servico_fotos",
        joinColumns = @JoinColumn(name = "servico_id")
    )
    @Column(name = "url", columnDefinition = "TEXT")
    @Size(max = 3, message = "Máximo 3 fotos por serviço.")
    private List<String> fotos = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        if (this.datacriacao == null) {
            this.datacriacao = LocalDateTime.now();
        }
        if (this.nota == null) {
            this.nota = BigDecimal.ZERO;
        }
    }
}
