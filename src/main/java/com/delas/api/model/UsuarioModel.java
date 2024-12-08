package com.delas.api.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "usuario")
public class UsuarioModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", length = 100, nullable = false)
    @NotBlank(message = "O nome é obrigatório.")
    private String nome;

    @Column(name = "email", length = 100, nullable = false, unique = true)
    @NotBlank(message = "O email é obrigatório.")
    private String email;

    @Column(name = "senha", length = 80, nullable = false)
    @NotBlank(message = "A senha é obrigatória.")
    private String senha;

    @Column(name = "telefone", length = 15, unique = true)
    @NotBlank(message = "O telefone é obrigatório.")
    private String telefone;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TipoUsuario tipo;

    @Column(name = "bairro", length = 45)
    private String bairro;

    @Column(name = "cep", length = 20, nullable = false)
    @NotBlank(message = "O cep é obrigatório.")
    private String cep;

    @Column(name = "rua", length = 100) // Adicionado o campo "rua"
    private String rua;  // Novo atributo rua

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date datacriacao = new Date(); // Gera a data automaticamente

    @Column(name = "cpf", nullable = false, unique = true)
    @NotBlank(message = "O cpf é obrigatória.")
    private String cpf;

    // Lista de avaliações do usuário
    @ElementCollection
    private List<Integer> avaliacoes = new ArrayList<>();



    // Enum para o tipo de usuário
    public enum TipoUsuario {
        ADMIN,
        CLIENTE,
        PRESTADOR
    }

}
