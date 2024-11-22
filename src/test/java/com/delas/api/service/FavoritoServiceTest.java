
        package com.delas.api.service;

import com.delas.api.model.FavoritoModel;
import com.delas.api.repository.FavoritoRepository;
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

class FavoritoServiceTest {

    @InjectMocks
    private FavoritoService favoritoService;

    @Mock
    private FavoritoRepository favoritoRepository;

    private FavoritoModel favorito;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        favorito = new FavoritoModel();
        favorito.setIdfavorito(1L);
        favorito.setDatafavoritamento(LocalDateTime.now());
        // Mock de `ServicoFavorito` e `UsuarioFavorito` podem ser configurados aqui
    }

    @Test
    void testSave() {
        when(favoritoRepository.save(favorito)).thenReturn(favorito);

        FavoritoModel savedFavorito = favoritoService.save(favorito);

        assertNotNull(savedFavorito);
        assertEquals(favorito.getIdfavorito(), savedFavorito.getIdfavorito());
        verify(favoritoRepository, times(1)).save(favorito);
    }

    @Test
    void testFindAll() {
        List<FavoritoModel> favoritos = Arrays.asList(favorito, new FavoritoModel());
        when(favoritoRepository.findAll()).thenReturn(favoritos);

        List<FavoritoModel> result = favoritoService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(favoritoRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        when(favoritoRepository.findById(1L)).thenReturn(Optional.of(favorito));

        FavoritoModel result = favoritoService.findById(1L);

        assertNotNull(result);
        assertEquals(favorito.getIdfavorito(), result.getIdfavorito());
        verify(favoritoRepository, times(1)).findById(1L);
    }

    @Test
    void testFindById_NotFound() {
        when(favoritoRepository.findById(1L)).thenReturn(Optional.empty());

        FavoritoModel result = favoritoService.findById(1L);

        assertNull(result);
        verify(favoritoRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdate() {
        FavoritoModel updatedFavorito = new FavoritoModel();
        updatedFavorito.setIdfavorito(1L);
        updatedFavorito.setDatafavoritamento(LocalDateTime.now());

        when(favoritoRepository.findById(1L)).thenReturn(Optional.of(favorito));
        when(favoritoRepository.save(favorito)).thenReturn(updatedFavorito);

        FavoritoModel result = favoritoService.update(1L, updatedFavorito);

        assertNotNull(result);
        assertEquals(updatedFavorito.getDatafavoritamento(), result.getDatafavoritamento());
        verify(favoritoRepository, times(1)).findById(1L);
        verify(favoritoRepository, times(1)).save(favorito);
    }

    @Test
    void testUpdate_NotFound() {
        FavoritoModel updatedFavorito = new FavoritoModel();
        when(favoritoRepository.findById(1L)).thenReturn(Optional.empty());

        FavoritoModel result = favoritoService.update(1L, updatedFavorito);

        assertNull(result);
        verify(favoritoRepository, times(1)).findById(1L);
        verify(favoritoRepository, times(0)).save(any());
    }

    @Test
    void testDeleteById() {
        when(favoritoRepository.existsById(1L)).thenReturn(true);

        boolean result = favoritoService.deleteById(1L);

        assertTrue(result);
        verify(favoritoRepository, times(1)).existsById(1L);
        verify(favoritoRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteById_NotFound() {
        when(favoritoRepository.existsById(1L)).thenReturn(false);

        boolean result = favoritoService.deleteById(1L);

        assertFalse(result);
        verify(favoritoRepository, times(1)).existsById(1L);
        verify(favoritoRepository, times(0)).deleteById(anyLong());
    }
}

