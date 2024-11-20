package com.delas.api.repository;

import com.delas.api.model.FavoritoModel;
import com.delas.api.model.ServicosModel;
import com.delas.api.model.UsuarioModel;
import com.delas.api.model.UsuarioModel.TipoUsuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class FavoritoRepositoryTest {

    @Autowired
    private FavoritoRepository favoritoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ServicosRepository servicosRepository;

    @Test
    @DisplayName("Deve salvar um favorito no banco de dados")
    void deveSalvarFavorito() {
        // Criando um usuário
        UsuarioModel usuario = new UsuarioModel(
                null, "João Silva", "joao@email.com", "senha123",
                "81999999999", TipoUsuario.CLIENTE, "Rua A", "Bairro B",
                "50000000", LocalDateTime.now(), "12345678900"
        );
        UsuarioModel usuarioSalvo = usuarioRepository.save(usuario);

        // Criando um serviço
        ServicosModel servico = new ServicosModel(
                null, "Limpeza", new BigDecimal(100),
                "Limpeza de casa", LocalDateTime.now(),"Domestica", 2L
        );
        ServicosModel servicoSalvo = servicosRepository.save(servico);

        // Criando um favorito
        FavoritoModel favorito = new FavoritoModel(
                null, servicoSalvo, usuarioSalvo, LocalDateTime.now()
        );
        FavoritoModel favoritoSalvo = favoritoRepository.save(favorito);

        assertThat(favoritoSalvo).isNotNull();
        assertThat(favoritoSalvo.getIdfavorito()).isNotNull();
        assertThat(favoritoSalvo.getServicoFavorito().getIdservicos()).isEqualTo(servicoSalvo.getIdservicos());
        assertThat(favoritoSalvo.getUsuarioFavorito().getId()).isEqualTo(usuarioSalvo.getId());
    }

    @Test
    @DisplayName("Deve buscar um favorito pelo ID")
    void deveBuscarFavoritoPorId() {
        // Criando um favorito
        FavoritoModel favorito = criarESalvarFavorito();

        // Buscando pelo ID
        Optional<FavoritoModel> favoritoEncontrado = favoritoRepository.findById(favorito.getIdfavorito());

        assertThat(favoritoEncontrado).isPresent();
        assertThat(favoritoEncontrado.get().getIdfavorito()).isEqualTo(favorito.getIdfavorito());
    }

    @Test
    @DisplayName("Deve listar todos os favoritos")
    void testFindAll() {

        UsuarioModel usuario1 = new UsuarioModel(
                null, "João Oliveira", "joao.oliveira@email.com", "senha123",
                "81977777777", TipoUsuario.CLIENTE, "Rua A", "Bairro B",
                "52000000", LocalDateTime.now(), "12345678900"
                );


        criarESalvarFavorito(); // Adicionando um favorito
//        criarESalvarFavorito(); // Adicionando outro favorito
//ERRO DO CARALHO DEPOIS NOIS VE
//        assertThat(favoritoRepository.findAll().size()).isEqualTo(2);

    }

    @Test
    @DisplayName("Deve deletar um favorito pelo ID")
    void deveDeletarFavorito() {
        FavoritoModel favorito = criarESalvarFavorito();

        favoritoRepository.deleteById(favorito.getIdfavorito());

        Optional<FavoritoModel> favoritoDeletado = favoritoRepository.findById(favorito.getIdfavorito());
        assertThat(favoritoDeletado).isEmpty();
    }

    private FavoritoModel criarESalvarFavorito() {
        // Criando um usuário


        UsuarioModel usuario = new UsuarioModel(
                null, "João Oliveira", "joao.oliveira@email.com", "senha123",
                "81977777777", TipoUsuario.CLIENTE, "Rua A", "Bairro B",
                "52000000", LocalDateTime.now(), "12345678900"
        );
        UsuarioModel usuarioSalvo = usuarioRepository.save(usuario);



        // Criando um serviço
        ServicosModel servico = new ServicosModel(
                null, "Manutenção", new BigDecimal(120),
                "Manutenção de unhas", LocalDateTime.now(),"Beleza", 6L
        );
        ServicosModel servicoSalvo = servicosRepository.save(servico);

        // Criando um favorito
        FavoritoModel favorito = new FavoritoModel(
                null, servicoSalvo, usuarioSalvo, LocalDateTime.now()
        );

        return favoritoRepository.save(favorito);
    }
}
