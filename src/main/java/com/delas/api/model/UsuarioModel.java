
package com.delas.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "usuario", indexes = {
    @Index(name = "idx_usuario_email", columnList = "email"),
    @Index(name = "idx_usuario_cpf", columnList = "cpf"),
    @Index(name = "idx_usuario_telefone", columnList = "telefone")
})
public class UsuarioModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", length = 100, nullable = false)
    @NotBlank(message = "O nome é obrigatório.")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres.")
    private String nome;

    @Column(name = "email", length = 100, nullable = false, unique = true)
    @NotBlank(message = "O email é obrigatório.")
    @Email(message = "Email inválido.")
    private String email;

    @Column(name = "senha", nullable = false, columnDefinition = "TEXT")
    @NotBlank(message = "A senha é obrigatória.")
    private String senha;

    @Column(name = "telefone", length = 15, unique = true, nullable = false)
    @NotBlank(message = "O telefone é obrigatório.")
    private String telefone;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false, columnDefinition = "VARCHAR(20)")
    private TipoUsuario tipo;

    @Column(name = "bairro", length = 45)
    private String bairro;

    @Column(name = "cep", length = 20, nullable = false)
    @NotBlank(message = "O CEP é obrigatório.")
    private String cep;

    @Column(name = "rua", length = 100)
    private String rua;

    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    @Column(name = "cpf", nullable = false, unique = true, length = 14)
    @NotBlank(message = "O CPF é obrigatório.")
    private String cpf;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "usuario_avaliacoes", joinColumns = @JoinColumn(name = "usuario_id"))
    @Column(name = "avaliacao")
    private List<Integer> avaliacoes = new ArrayList<>();

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ServicosModel> servicos = new ArrayList<>();

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ContratacaoModel> contratacoes = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        if (this.dataCriacao == null) {
            this.dataCriacao = LocalDateTime.now();
        }
    }

    public enum TipoUsuario {
        ADMIN,
        CLIENTE,
        PRESTADOR
    }
}