package com.delas.api.model;

import jakarta.persistence.*;
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
    private String nome;

    @Column(name = "email", length = 100, nullable = false, unique = true)
    private String email;

    @Column(name = "senha", length = 80, nullable = false)
    private String senha;

    @Column(name = "telefone", length = 15, unique = true)
    private String telefone;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TipoUsuario tipo;

    @Column(name = "bairro", length = 45)
    private String bairro;

    @Column(name = "cep", length = 20, unique = true)
    private String cep;

    @Column(name = "rua", length = 100) // Adicionado o campo "rua"
    private String rua;  // Novo atributo rua

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date datacriacao = new Date(); // Gera a data automaticamente

    @Column(name = "cpf", nullable = false, unique = true)
    private String cpf;

    // Lista de avaliações do usuário
    @ElementCollection
    private List<Integer> avaliacoes = new ArrayList<>();

    // Relacionamento com ContratacaoModel
    @OneToMany(mappedBy = "id", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ContratacaoModel> contratacoes = new ArrayList<>();

    // Enum para o tipo de usuário
    public enum TipoUsuario {
        ADMIN,
        CLIENTE,
        PRESTADOR
    }

    // Enum para o gênero (Removido na versão final)
    public enum Genero {
        MULHER_CIS,
        MULHER_TRANS,
        PREFIRO_NAO_RESPONDER
    }

    // Método para calcular o ranking
    public String determinarRanking() {
        double media = avaliacoes.stream().mapToInt(Integer::intValue).average().orElse(0);
        int quantidadeServicos = contratacoes.size();

        if (media >= 4.5 && quantidadeServicos >= 50) {
            return "⭐⭐⭐⭐⭐";
        } else if (media >= 4.0 && quantidadeServicos >= 30) {
            return "⭐⭐⭐⭐";
        } else if (media >= 3.5 && quantidadeServicos >= 20) {
            return "⭐⭐⭐";
        } else if (media >= 3.0 && quantidadeServicos >= 10) {
            return "⭐⭐";
        } else {
            return "⭐";
        }
    }

}
   
