package com.delas.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "contratacao", indexes = {
    @Index(name = "idx_contratacao_usuario_id", columnList = "usuario_id"),
    @Index(name = "idx_contratacao_servicos_id", columnList = "idservicos"),
    @Index(name = "idx_contratacao_status", columnList = "status")
})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContratacaoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idcontratacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private UsuarioModel usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idservicos", nullable = false)
    private ServicosModel servico;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, columnDefinition = "VARCHAR(30)")
    private StatusContratacao status;

    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDateTime dataContratacao = LocalDateTime.now();

    @Column(name = "comentarios", columnDefinition = "TEXT")
    private String comentarios;

    @PrePersist
    protected void onCreate() {
        if (this.dataContratacao == null) {
            this.dataContratacao = LocalDateTime.now();
        }
    }

    public enum StatusContratacao {
        AGUARDANDO_RESPOSTA,
        ACEITA,
        REJEITADA,
        FINALIZADA,
        CANCELADA
    }
}
