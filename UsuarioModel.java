package com.delas.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.Date;

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
    private String nome;

    @Enumerated(EnumType.STRING) // Usando Enum para garantir consistência
    @Column(name = "genero", length = 50, nullable = false)
    private Genero genero;

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

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date datacriacao = new Date(); // Gera a data automaticamente

    @Column(name = "cpf", nullable = false, unique = true)
    private String cpf;

    // Enum para o tipo de usuário
    public enum TipoUsuario {
        ADMIN,
        CLIENTE,
        PRESTADOR
    }

    // Enum para o gênero
    public enum Genero {
        MULHER_CIS,
        MULHER_TRANS,
        PREFIRO_NAO_RESPONDER
    }

    // Método de exemplo para criar um usuário
    public static UsuarioModel criarUsuarioExemplo() {
        UsuarioModel usuario = new UsuarioModel();
        usuario.setNome("João da Silva");
        usuario.setEmail("joao.silva@email.com");
        usuario.setSenha("senha123");
        usuario.setTelefone("1234567890");
        usuario.setTipo(TipoUsuario.CLIENTE);
        usuario.setGenero(Genero.MULHER_CIS);  // Defina o gênero conforme necessário
        usuario.setBairro("Bairro Teste");
        usuario.setCep("12345-678");
        usuario.setDatacriacao(new Date());
        usuario.setCpf("123.456.789-00");
        return usuario;
    }
}
