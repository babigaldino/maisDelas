package com.delas.api.service;

import com.delas.api.model.AvaliacaoModel;
import com.delas.api.repository.AvaliacaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AvaliacaoServiceTest {

    @InjectMocks
    private AvaliacaoService avaliacaoService;

    @Mock
    private AvaliacaoRepository avaliacaoRepository;

    private AvaliacaoModel avaliacao;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        avaliacao = new AvaliacaoModel();
        avaliacao.setIdavaliacao(1L);
        avaliacao.setNota(5);
        // Pode adicionar mock para ContratacaoModel se necessário
    }

    @Test
    void testSave() {
        when(avaliacaoRepository.save(avaliacao)).thenReturn(avaliacao);

        AvaliacaoModel savedAvaliacao = avaliacaoService.save(avaliacao);

        assertNotNull(savedAvaliacao);
        assertEquals(avaliacao.getIdavaliacao(), savedAvaliacao.getIdavaliacao());
        assertEquals(avaliacao.getNota(), savedAvaliacao.getNota());
        verify(avaliacaoRepository, times(1)).save(avaliacao);
    }

    @Test
    void testFindAll() {
        List<AvaliacaoModel> avaliacoes = Arrays.asList(avaliacao, new AvaliacaoModel());
        when(avaliacaoRepository.findAll()).thenReturn(avaliacoes);

        List<AvaliacaoModel> result = avaliacaoService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(avaliacaoRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        when(avaliacaoRepository.findById(1L)).thenReturn(Optional.of(avaliacao));

        Optional<AvaliacaoModel> result = avaliacaoService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(avaliacao.getIdavaliacao(), result.get().getIdavaliacao());
        assertEquals(avaliacao.getNota(), result.get().getNota());
        verify(avaliacaoRepository, times(1)).findById(1L);
    }

    @Test
    void testFindById_NotFound() {
        when(avaliacaoRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<AvaliacaoModel> result = avaliacaoService.findById(1L);

        assertFalse(result.isPresent());
        verify(avaliacaoRepository, times(1)).findById(1L);
    }
}
