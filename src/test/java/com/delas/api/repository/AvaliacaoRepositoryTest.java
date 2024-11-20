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

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class AvaliacaoRepositoryTest {

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    @Autowired
    private ContratacaoRepository contratacaoRepository;

    @Autowired
    private ServicosRepository servicosRepository;

    @Autowired
    private UsuarioRepository usuarioRepository; // Adicione a injeção do repositório de usuário

    private ContratacaoModel contratacao;
    private ContratacaoModel contratacao1;

    @BeforeEach
    void setup() {
        // Criando um usuário de teste
        UsuarioModel usuario = new UsuarioModel(

        null, "João Oliveira", "joao.oliveira@email.com", "senha123",
                "81977777777", UsuarioModel.TipoUsuario.CLIENTE, "Rua A", "Bairro B",
                "52000000", new Date(), "12345678900");

       ServicosModel servico = new ServicosModel(
                null, // O id será gerado automaticamente
                "Descrição do serviço",
                BigDecimal.valueOf(100.0),
                "Serviço de Teste",
                LocalDateTime.now(),
                "Categoria Teste",
                1L
        ); // nao tinha um servico criado, logo, quando ia referenciar o idservico abaixo, como ta not null, nao rodava nada

        // Salve o usuário no banco de dados
        usuario = usuarioRepository.save(usuario);
        servico = servicosRepository.save(servico);

        // Criando uma contratação de teste com o usuário salvo
        contratacao = new ContratacaoModel(
                null, usuario, servico, "Pendente", LocalDateTime.now(), "Nenhum comentário"
        );

       contratacao1 = new ContratacaoModel(
                null, usuario, servico, "Pendente", LocalDateTime.now(), "comentário"
        ); // criando outra contrataçãp pra ter um id diferete pra resolver o problema

        contratacao = contratacaoRepository.save(contratacao);
        contratacao1 = contratacaoRepository.save(contratacao1);
    }

    @Test
    @DisplayName("Deve salvar uma nova avaliação")
    void testSaveAvaliacao() {
        AvaliacaoModel avaliacao = new AvaliacaoModel(
                null, contratacao, 5
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
                null, contratacao, 5
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
                null, contratacao, 5
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
                null, contratacao, 5
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
                null, contratacao, 5
        );

        AvaliacaoModel avaliacao2 = new AvaliacaoModel(
                null, contratacao1, 3
        ); //tava dando erro pq so tinha uma contratacao criada, e o mesmo id tava referenciando as duas avaliacoes

        avaliacaoRepository.save(avaliacao1);
        avaliacaoRepository.save(avaliacao2);

        var avaliacoes = avaliacaoRepository.findAll();
        assertEquals(2, avaliacoes.size());
    }
}