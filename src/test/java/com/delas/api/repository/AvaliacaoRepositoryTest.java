package com.delas.api.repository;

import com.delas.api.model.AvaliacaoModel;
import com.delas.api.model.ContratacaoModel;
import com.delas.api.model.ServicosModel;
import com.delas.api.model.UsuarioModel;
import com.delas.api.repository.AvaliacaoRepository;
import com.delas.api.repository.ContratacaoRepository;
import com.delas.api.repository.UsuarioRepository; // Adicione o repositório de usuário
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class AvaliacaoRepositoryTest {

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    @Autowired
    private ContratacaoRepository contratacaoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository; // Adicione a injeção do repositório de usuário

    private ContratacaoModel contratacao;

    @BeforeEach
    void setup() {
        // Criando um usuário de teste
        UsuarioModel usuario = new UsuarioModel();
        // Defina os atributos necessários do usuario aqui, como nome, email, etc.
        usuario.setNome("Usuário de Teste");
        usuario.setEmail("usuario@teste.com");
        // Salve o usuário no banco de dados
        usuario = usuarioRepository.save(usuario);

        // Criando uma contratação de teste com o usuário salvo
        contratacao = new ContratacaoModel(
                0L, usuario, new ServicosModel(), "Pendente", LocalDateTime.now(), "Nenhum comentário"
        );
        contratacao = contratacaoRepository.save(contratacao);
    }

    @Test
    @DisplayName("Deve salvar uma nova avaliação")
    void testSaveAvaliacao() {
        AvaliacaoModel avaliacao = new AvaliacaoModel(
                0L, contratacao, 5
        );

        AvaliacaoModel savedAvaliacao = avaliacaoRepository.save(avaliacao);

        assertNotNull(savedAvaliacao.getIdavaliacao());
        assertEquals(5, savedAvaliacao.getNota());
        assertEquals(contratacao.getIdcontratacao(), savedAvaliacao.getIdcontratacao().getIdcontratacao());
    }

    @Test
    @DisplayName("Deve encontrar uma avaliação pelo ID")
    void testFindById() {
        AvaliacaoModel avaliacao = new AvaliacaoModel(
                0L, contratacao, 5
        );

        AvaliacaoModel savedAvaliacao = avaliacaoRepository.save(avaliacao);
        Optional<AvaliacaoModel> foundAvaliacao = avaliacaoRepository.findById(savedAvaliacao.getIdavaliacao());

        assertTrue(foundAvaliacao.isPresent());
        assertEquals(savedAvaliacao.getIdavaliacao(), foundAvaliacao.get().getIdavaliacao());
    }

    @Test
    @DisplayName("Deve atualizar uma avaliação existente")
    void testUpdateAvaliacao() {
        AvaliacaoModel avaliacao = new AvaliacaoModel(
                0L, contratacao, 5
        );

        AvaliacaoModel savedAvaliacao = avaliacaoRepository.save(avaliacao);
        savedAvaliacao.setNota(4);
        AvaliacaoModel updatedAvaliacao = avaliacaoRepository.save(savedAvaliacao);

        assertEquals(4, updatedAvaliacao.getNota());
    }

    @Test
    @DisplayName("Deve deletar uma avaliação pelo ID")
    void testDeleteById() {
        AvaliacaoModel avaliacao = new AvaliacaoModel(
                0L, contratacao, 5
        );

        AvaliacaoModel savedAvaliacao = avaliacaoRepository.save(avaliacao);
        avaliacaoRepository.deleteById(savedAvaliacao.getIdavaliacao());

        Optional<AvaliacaoModel> foundAvaliacao = avaliacaoRepository.findById(savedAvaliacao.getIdavaliacao());
        assertFalse(foundAvaliacao.isPresent());
    }

    @Test
    @DisplayName("Deve listar todas as avaliações")
    void testFindAll() {
        AvaliacaoModel avaliacao1 = new AvaliacaoModel(
                0L, contratacao, 5
        );

        AvaliacaoModel avaliacao2 = new AvaliacaoModel(
                0L, contratacao, 3
        );

        avaliacaoRepository.save(avaliacao1);
    }
}