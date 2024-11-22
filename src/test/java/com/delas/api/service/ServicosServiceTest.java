
        package com.delas.api.service;

import com.delas.api.model.ServicosModel;
import com.delas.api.repository.ServicosRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
        import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ServicosServiceTest {

    @Mock
    private ServicosRepository servicosRepository;

    @InjectMocks
    private ServicosService servicosService;

    private ServicosModel servico;

    @BeforeEach
    public void setUp() {
        // Inicializando o ServicosModel para ser utilizado nos testes
        servico = new ServicosModel();
        servico.setIdservicos(1L);
        servico.setDescricao("Serviço de Teste");
        servico.setPreco(new BigDecimal("100.00"));
        servico.setTitulo("Teste");
        servico.setDatacriacao(LocalDateTime.now());
        servico.setCategoria("Categoria Teste");
        servico.setIdfavorito(1L);
    }

    @Test
    public void testSave() {
        // Mockando o comportamento do método save
        when(servicosRepository.save(servico)).thenReturn(servico);

        // Chamando o método do serviço
        ServicosModel savedServico = servicosService.save(servico);

        // Verificando o resultado
        assertNotNull(savedServico);
        assertEquals("Teste", savedServico.getTitulo());
        verify(servicosRepository, times(1)).save(servico); // Verifica se o método save foi chamado uma vez
    }

    @Test
    public void testFindAll() {
        // Mockando o comportamento do método findAll
        when(servicosRepository.findAll()).thenReturn(List.of(servico));

        // Chamando o método do serviço
        List<ServicosModel> servicos = servicosService.findAll();

        // Verificando o resultado
        assertFalse(servicos.isEmpty());
        assertEquals(1, servicos.size());
        verify(servicosRepository, times(1)).findAll(); // Verifica se o método findAll foi chamado uma vez
    }

    @Test
    public void testFindById() {
        // Mockando o comportamento do método findById
        when(servicosRepository.findById(1L)).thenReturn(Optional.of(servico));

        // Chamando o método do serviço
        Optional<ServicosModel> foundServico = servicosService.findById(1L);

        // Verificando o resultado
        assertTrue(foundServico.isPresent());
        assertEquals("Teste", foundServico.get().getTitulo());
        verify(servicosRepository, times(1)).findById(1L); // Verifica se o método findById foi chamado uma vez
    }

    @Test
    public void testUpdate() {
        // Mockando o comportamento dos métodos findById e save
        when(servicosRepository.findById(1L)).thenReturn(Optional.of(servico));
        when(servicosRepository.save(servico)).thenReturn(servico);

        // Modificando o ServicosModel para testar a atualização
        servico.setDescricao("Serviço Atualizado");

        // Chamando o método do serviço
        Optional<ServicosModel> updatedServico = servicosService.update(1L, servico);

        // Verificando o resultado
        assertTrue(updatedServico.isPresent());
        assertEquals("Serviço Atualizado", updatedServico.get().getDescricao());
        verify(servicosRepository, times(1)).findById(1L); // Verifica se o findById foi chamado uma vez
        verify(servicosRepository, times(1)).save(servico); // Verifica se o save foi chamado uma vez
    }

    @Test
    public void testDeleteById() {
        // Mockando o comportamento do existsById
        when(servicosRepository.existsById(1L)).thenReturn(true);

        // Chamando o método do serviço
        boolean isDeleted = servicosService.deleteById(1L);

        // Verificando o resultado
        assertTrue(isDeleted);
        verify(servicosRepository, times(1)).existsById(1L); // Verifica se o existsById foi chamado uma vez
        verify(servicosRepository, times(1)).deleteById(1L); // Verifica se o deleteById foi chamado uma vez
    }

    @Test
    public void testDeleteByIdNotFound() {
        // Mockando o comportamento do existsById
        when(servicosRepository.existsById(1L)).thenReturn(false);

        // Chamando o método do serviço
        boolean isDeleted = servicosService.deleteById(1L);

        // Verificando o resultado
        assertFalse(isDeleted);
        verify(servicosRepository, times(1)).existsById(1L); // Verifica se o existsById foi chamado uma vez
        verify(servicosRepository, times(0)).deleteById(1L); // Verifica se o deleteById não foi chamado
    }
}
