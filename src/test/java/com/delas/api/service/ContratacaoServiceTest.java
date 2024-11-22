
        package com.delas.api.service;

import com.delas.api.model.ContratacaoModel;
import com.delas.api.repository.ContratacaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
        import static org.mockito.Mockito.*;

class ContratacaoServiceTest {

    @InjectMocks
    private ContratacaoService contratacaoService;

    @Mock
    private ContratacaoRepository contratacaoRepository;

    private ContratacaoModel contratacao;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        contratacao = new ContratacaoModel();
        contratacao.setIdcontratacao(1L);
        contratacao.setStatus("Pendente");
        contratacao.setDataContratacao(LocalDateTime.now());
        contratacao.setComentarios("Teste de contratação");
        // Usuário e Serviço podem ser mockados conforme necessário
    }

    @Test
    void testSave() {
        when(contratacaoRepository.save(contratacao)).thenReturn(contratacao);

        ContratacaoModel savedContratacao = contratacaoService.save(contratacao);

        assertNotNull(savedContratacao);
        assertEquals(contratacao.getIdcontratacao(), savedContratacao.getIdcontratacao());
        verify(contratacaoRepository, times(1)).save(contratacao);
    }

    @Test
    void testFindAll() {
        List<ContratacaoModel> contratacoes = Arrays.asList(contratacao, new ContratacaoModel());
        when(contratacaoRepository.findAll()).thenReturn(contratacoes);

        List<ContratacaoModel> result = contratacaoService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(contratacaoRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        when(contratacaoRepository.findById(1L)).thenReturn(Optional.of(contratacao));

        Optional<ContratacaoModel> result = contratacaoService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(contratacao.getIdcontratacao(), result.get().getIdcontratacao());
        verify(contratacaoRepository, times(1)).findById(1L);
    }

    @Test
    void testDeleteById() {
        when(contratacaoRepository.existsById(1L)).thenReturn(true);
        doNothing().when(contratacaoRepository).deleteById(1L);

        boolean result = contratacaoService.deleteById(1L);

        assertTrue(result);
        verify(contratacaoRepository, times(1)).existsById(1L);
        verify(contratacaoRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteById_NotFound() {
        when(contratacaoRepository.existsById(1L)).thenReturn(false);

        boolean result = contratacaoService.deleteById(1L);

        assertFalse(result);
        verify(contratacaoRepository, times(1)).existsById(1L);
        verify(contratacaoRepository, never()).deleteById(anyLong());
    }
}

