package com.delas.api.repository;

import com.delas.api.model.ServicosModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Transactional // Garante que a transação seja revertida após o teste
public class ServicosRepositoryTest {

    @Autowired
    private ServicosRepository servicosRepository;

    private ServicosModel servico;

    @BeforeEach
    public void setUp() {
        // Criação de um novo objeto ServicosModel com o construtor do Lombok
        servico = new ServicosModel(
                null, // O id será gerado automaticamente
                "Descrição do serviço",
                BigDecimal.valueOf(100.0),
                "Serviço de Teste",
                LocalDateTime.now(),
                "Categoria Teste",
                1L
        );
    }

    @Test
    public void testSalvarServico() {
        // Salva o objeto no banco de dados
        ServicosModel servicoSalvo = servicosRepository.save(servico);

        // Verifica se o id foi gerado corretamente após o salvamento
        assertNotNull(servicoSalvo.getIdservicos());
        assertEquals(servico.getTitulo(), servicoSalvo.getTitulo());
        assertEquals(servico.getDescricao(), servicoSalvo.getDescricao());
        assertEquals(servico.getPreco(), servicoSalvo.getPreco());
    }

    @Test
    public void testBuscarPorId() {
        // Salva o objeto no banco de dados
        ServicosModel servicoSalvo = servicosRepository.save(servico);

        // Busca o objeto pelo id
        ServicosModel servicoEncontrado = servicosRepository.findById(servicoSalvo.getIdservicos()).orElse(null);

        // Verifica se o serviço encontrado tem os mesmos dados que o salvo
        assertNotNull(servicoEncontrado);
        assertEquals(servicoSalvo.getIdservicos(), servicoEncontrado.getIdservicos());
        assertEquals(servicoSalvo.getTitulo(), servicoEncontrado.getTitulo());
    }

    @Test
    public void testAtualizarServico() {
        // Salva o objeto no banco de dados
        ServicosModel servicoSalvo = servicosRepository.save(servico);

        // Atualiza um campo do serviço
        servicoSalvo.setDescricao("Nova descrição do serviço");

        // Salva novamente para aplicar a atualização
        ServicosModel servicoAtualizado = servicosRepository.save(servicoSalvo);

        // Verifica se o campo foi atualizado corretamente
        assertEquals("Nova descrição do serviço", servicoAtualizado.getDescricao());
    }

    @Test
    public void testDeletarServico() {
        // Salva o objeto no banco de dados
        ServicosModel servicoSalvo = servicosRepository.save(servico);

        // Deleta o serviço
        servicosRepository.delete(servicoSalvo);

        // Verifica se o serviço foi excluído
        assertFalse(servicosRepository.findById(servicoSalvo.getIdservicos()).isPresent());
    }
}
