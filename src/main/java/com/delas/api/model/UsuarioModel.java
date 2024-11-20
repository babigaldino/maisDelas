package com.delas.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table (name = "usuario")
public class UsuarioModel {


    @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "nome", length = 100, nullable = false)
        private String nome;

        @Column(name = "email", length = 100, nullable = false, unique = true)
        private String email;

        @Column(name = "senha", length = 80, nullable = false)
        private String senha;

        @Column(name = "telefone", length = 15)
        private String telefone;

        @Enumerated(EnumType.STRING)
        @Column(name = "tipo", nullable = false)
        private TipoUsuario tipo;

        @Column(name = "rua", length = 60)
        private String rua;

        @Column(name = "bairro", length = 45)
        private String bairro;

        @Column(name = "cep", length = 20)
        private String cep;

        @Column(name = "datacriacao", nullable = false)
        private LocalDateTime dataCriacao;

        @Column(name = "cpf", nullable = false, unique = true)
        private String cpf;


    public enum TipoUsuario {
        ADMIN,
        CLIENTE,
        PRESTADOR
    }
}



