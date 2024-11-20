package com.delas.api.repository;

import com.delas.api.model.ContratacaoModel;
import com.delas.api.model.ServicosModel;
import com.delas.api.model.UsuarioModel;
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
class ContratacaoRepositoryTest {

    @Autowired
    private ContratacaoRepository contratacaoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ServicosRepository servicosRepository;

    private UsuarioModel usuario;
    private ServicosModel servico;

    @BeforeEach
    void setup() {
        // Criando um usuário de teste
        usuario = new UsuarioModel(
                null, "Ana Costa", "ana.costa@email.com", "senha123",
                "81999999999", UsuarioModel.TipoUsuario.CLIENTE, "Rua das Flores",
                "Bairro das Árvores", "51000000",
                new Date(), "12345678900"
        );
        usuario = usuarioRepository.save(usuario);

        // Criando um serviço de teste
        servico = new ServicosModel(
                null, "Serviço de Limpeza", new BigDecimal(100),
                "Limpeza geral para residências.", LocalDateTime.now(), "Doméstico", null
        );
        servico = servicosRepository.save(servico);
    }

    @Test
    @DisplayName("Deve salvar uma nova contratação no banco de dados")
    void testSaveContratacao() {
        ContratacaoModel contratacao = new ContratacaoModel(
                0L, usuario, servico, "Pendente", LocalDateTime.now(), "Sem comentários"
        );

        ContratacaoModel savedContratacao = contratacaoRepository.save(contratacao);

        assertNotNull(savedContratacao.getIdcontratacao());
        assertEquals("Pendente", savedContratacao.getStatus());
        assertEquals(usuario.getId(), savedContratacao.getId().getId());
        assertEquals(servico.getIdservicos(), savedContratacao.getIdservicos().getIdservicos());
    }

    @Test
    @DisplayName("Deve encontrar uma contratação pelo ID")
    void testFindById() {
        ContratacaoModel contratacao = new ContratacaoModel(
                0L, usuario, servico, "Pendente", LocalDateTime.now(), "Sem comentários"
        );

        ContratacaoModel savedContratacao = contratacaoRepository.save(contratacao);
        Optional<ContratacaoModel> foundContratacao = contratacaoRepository.findById(savedContratacao.getIdcontratacao());

        assertTrue(foundContratacao.isPresent());
        assertEquals(savedContratacao.getIdcontratacao(), foundContratacao.get().getIdcontratacao());
    }

    @Test
    @DisplayName("Deve deletar uma contratação pelo ID")
    void testDeleteById() {
        ContratacaoModel contratacao = new ContratacaoModel(
                0L, usuario, servico, "Pendente", LocalDateTime.now(), "Sem comentários"
        );

        ContratacaoModel savedContratacao = contratacaoRepository.save(contratacao);
        contratacaoRepository.deleteById(savedContratacao.getIdcontratacao());

        Optional<ContratacaoModel> foundContratacao = contratacaoRepository.findById(savedContratacao.getIdcontratacao());
        assertFalse(foundContratacao.isPresent());
    }

    @Test
    @DisplayName("Deve listar todas as contratações")
    void testFindAll() {
        ContratacaoModel contratacao1 = new ContratacaoModel(
                0L, usuario, servico, "Pendente", LocalDateTime.now(), "Sem comentários"
        );

        ContratacaoModel contratacao2 = new ContratacaoModel(
                0L, usuario, servico, "Finalizado", LocalDateTime.now(), "Trabalho excelente"
        );

        contratacaoRepository.save(contratacao1);
        contratacaoRepository.save(contratacao2);

        var contratacoes = contratacaoRepository.findAll();

        assertEquals(2, contratacoes.size());
    }
}
