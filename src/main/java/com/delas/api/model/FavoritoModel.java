package com.delas.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Table(name = "favorito")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FavoritoModel {

    @Id  // Define o campo como a chave primária da tabela
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    // Define a estratégia de geração automática do valor da chave primária (auto-incremento)
    private Long idavaliacao;  // A chave primária, que é do tipo Long


    @ManyToOne     // Define um relacionamento "muitos para um" entre FavoritoModel e ServicosModel (um serviço pode ser favoritado por muitos usuários)
    @JoinColumn(name = "idservicofavorito", nullable = false)  // Define a coluna que representa a chave estrangeira (idservicofavorito) e especifica que não pode ser nula
    private ServicosModel servicoFavorito;  // Relacionamento com a tabela "servicos", ou seja, um serviço que é favoritado

    @ManyToOne
    // Define um relacionamento "muitos para um" entre FavoritoModel e UsuarioModel (um usuário pode favoritar muitos serviços)
    @JoinColumn(name = "idprestadorfavorito", nullable = false)
    // Define a coluna que representa a chave estrangeira (idprestadorfavorito) e especifica que não pode ser nula
    private UsuarioModel usuarioFavorito;  // Relacionamento com a tabela "usuarios", ou seja, o usuário que favoritou o serviço

    private LocalDateTime dataFavoritamento = LocalDateTime.now();  // Data e hora em que o serviço foi favoritado

}
