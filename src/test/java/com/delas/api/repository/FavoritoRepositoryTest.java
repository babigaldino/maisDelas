package com.delas.api.repository;

import com.delas.api.model.FavoritoModel;
import com.delas.api.model.ServicosModel;
import com.delas.api.model.UsuarioModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class FavoritoRepositoryTest {

    @Autowired
    private FavoritoRepository favoritoRepository;

    @Autowired
    private ServicosRepository servicosRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    @DisplayName("Deve salvar e buscar um favorito por ID")
    void testSaveAndFindById() {
        // Criando um usuário
        UsuarioModel usuario = new UsuarioModel(
                null, "João Silva", "joao@email.com", "senha123",
                "81999999999", UsuarioModel.TipoUsuario.CLIENTE, "Rua A", "Bairro B",
                "50000000", new Date(), "12345678901"
        );
        UsuarioModel usuarioSalvo = usuarioRepository.save(usuario);

        // Criando um serviço
        ServicosModel servico = new ServicosModel(
                null, "Manutenção", new BigDecimal(120),
                "Manutenção de equipamentos", LocalDateTime.now(),
                "Tecnologia", 6L
        );
        ServicosModel servicoSalvo = servicosRepository.save(servico);

        // Criando e salvando um favorito
        FavoritoModel favorito = new FavoritoModel(
                null, servicoSalvo, usuarioSalvo, LocalDateTime.now()
        );
        FavoritoModel favoritoSalvo = favoritoRepository.save(favorito);

        // Verificando se o favorito foi salvo e encontrado corretamente
        Optional<FavoritoModel> favoritoEncontrado = favoritoRepository.findById(favoritoSalvo.getIdfavorito());
        assertThat(favoritoEncontrado).isPresent();
        assertThat(favoritoEncontrado.get().getIdfavorito()).isEqualTo(favoritoSalvo.getIdfavorito());
    }

    @Test
    @DisplayName("Deve listar todos os favoritos")
    void testFindAll() {
        // Criando usuários
        UsuarioModel usuario1 = new UsuarioModel(
                null, "João Silva", "joao@email.com", "senha123",
                "81999999999", UsuarioModel.TipoUsuario.CLIENTE, "Rua A", "Bairro B",
                "50000000", new Date(), "12345678901"
        );
        UsuarioModel usuario2 = new UsuarioModel(
                null, "Maria Souza", "maria@email.com", "senha456",
                "81988888888", UsuarioModel.TipoUsuario.CLIENTE, "Rua C", "Bairro D",
                "52000000", new Date(), "98765432100"
        );
        usuarioRepository.save(usuario1);
        usuarioRepository.save(usuario2);

        // Criando serviços
        ServicosModel servico1 = new ServicosModel(
                null, "Manutenção", new BigDecimal(120),
                "Manutenção de equipamentos", LocalDateTime.now(),
                "Tecnologia", 6L
        );
        ServicosModel servico2 = new ServicosModel(
                null, "Design", new BigDecimal(150),
                "Criação de logos", LocalDateTime.now(),
                "Criatividade", 8L
        );
        servicosRepository.save(servico1);
        servicosRepository.save(servico2);

        // Salvando favoritos
        favoritoRepository.save(new FavoritoModel(null, servico1, usuario1, LocalDateTime.now()));
        favoritoRepository.save(new FavoritoModel(null, servico2, usuario2, LocalDateTime.now()));

        // Verificando a quantidade de favoritos salvos
        assertThat(favoritoRepository.findAll().size()).isEqualTo(2);
    }

    @Test
    @DisplayName("Deve excluir um favorito pelo ID")
    void testDeleteById() {
        // Criando um usuário
        UsuarioModel usuario = new UsuarioModel(
                null, "João Silva", "joao@email.com", "senha123",
                "81999999999", UsuarioModel.TipoUsuario.CLIENTE, "Rua A", "Bairro B",
                "50000000", new Date(), "12345678901"
        );
        UsuarioModel usuarioSalvo = usuarioRepository.save(usuario);

        // Criando um serviço
        ServicosModel servico = new ServicosModel(
                null, "Manutenção", new BigDecimal(120),
                "Manutenção de equipamentos", LocalDateTime.now(),
                "Tecnologia", 6L
        );
        ServicosModel servicoSalvo = servicosRepository.save(servico);

        // Criando e salvando um favorito
        FavoritoModel favorito = new FavoritoModel(
                null, servicoSalvo, usuarioSalvo, LocalDateTime.now()
        );
        FavoritoModel favoritoSalvo = favoritoRepository.save(favorito);

        // Excluindo o favorito
        favoritoRepository.deleteById(favoritoSalvo.getIdfavorito());

        // Verificando se foi removido
        Optional<FavoritoModel> favoritoExcluido = favoritoRepository.findById(favoritoSalvo.getIdfavorito());
        assertThat(favoritoExcluido).isEmpty();
    }
}
