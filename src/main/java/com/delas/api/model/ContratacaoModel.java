package com.delas.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "contratacao")
public class ContratacaoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // Altere para "id", assim o método getId() será gerado automaticamente pelo Lombok

    // Relacionamento com o usuário - mudado o nome da propriedade
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private UsuarioModel usuario;

    @ManyToOne
    @JoinColumn(name = "idservicos", nullable = false)
    private ServicosModel servicos;

    @Column(name = "status", length = 20)
    @NotNull
    @Size(min = 1, max = 20)
    private String status;

    @Column(name = "datacriacao", nullable = false)
    private LocalDateTime dataContratacao = LocalDateTime.now();

    @Column(name = "comentarios", nullable = false)
    @NotNull
    @Size(min = 1, message = "Comentários não podem ser vazios.")
    private String comentarios;

    // Relacionamento com a tabela Avaliação
    @OneToOne(mappedBy = "contratacao", cascade = CascadeType.PERSIST)
    private AvaliacaoModel avaliacao;
}
