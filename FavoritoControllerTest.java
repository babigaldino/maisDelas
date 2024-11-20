package com.delas.api.controller;

import com.delas.api.model.FavoritoModel;
import com.delas.api.service.FavoritoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class FavoritoControllerTest {

    private MockMvc mockMvc;

    @Mock
    private FavoritoService favoritoService;

    @InjectMocks
    private FavoritoController favoritoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(favoritoController).build();
    }

    @Test
    @DisplayName("Deve criar um novo favorito")
    void createFavorito() throws Exception {
        FavoritoModel favorito = new FavoritoModel();
        favorito.setId(1L);
        favorito.setNome("Produto Favorito");

        when(favoritoService.save(any(FavoritoModel.class))).thenReturn(favorito);

        mockMvc.perform(post("/favorito")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\": \"Produto Favorito\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Produto Favorito"));

        verify(favoritoService, times(1)).save(any(FavoritoModel.class));
    }

    @Test
    @DisplayName("Deve retornar todos os favoritos")
    void getAllFavoritos() throws Exception {
        mockMvc.perform(get("/favorito"))
                .andExpect(status().isOk());

        verify(favoritoService, times(1)).findAll();
    }

    @Test
    @DisplayName("Deve retornar um favorito pelo ID")
    void getFavoritoById() throws Exception {
        FavoritoModel favorito = new FavoritoModel();
        favorito.setId(1L);
        favorito.setNome("Produto Favorito");

        when(favoritoService.findById(1L)).thenReturn(favorito);

        mockMvc.perform(get("/favorito/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Produto Favorito"));

        verify(favoritoService, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Deve retornar 404 se favorito não for encontrado pelo ID")
    void getFavoritoByIdNotFound() throws Exception {
        when(favoritoService.findById(1L)).thenReturn(null);

        mockMvc.perform(get("/favorito/1"))
                .andExpect(status().isNotFound());

        verify(favoritoService, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Deve atualizar um favorito existente")
    void updateFavorito() throws Exception {
        FavoritoModel favorito = new FavoritoModel();
        favorito.setId(1L);
        favorito.setNome("Produto Favorito");

        FavoritoModel updatedFavorito = new FavoritoModel();
        updatedFavorito.setId(1L);
        updatedFavorito.setNome("Produto Atualizado");

        when(favoritoService.update(eq(1L), any(FavoritoModel.class))).thenReturn(updatedFavorito);

        mockMvc.perform(put("/favorito/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\": \"Produto Atualizado\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Produto Atualizado"));

        verify(favoritoService, times(1)).update(eq(1L), any(FavoritoModel.class));
    }

    @Test
    @DisplayName("Deve excluir um favorito pelo ID")
    void deleteFavorito() throws Exception {
        when(favoritoService.deleteById(1L)).thenReturn(true);

        mockMvc.perform(delete("/favorito/1"))
                .andExpect(status().isNoContent());

        verify(favoritoService, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Deve retornar 404 ao tentar excluir um favorito que não existe")
    void deleteFavoritoNotFound() throws Exception {
        when(favoritoService.deleteById(1L)).thenReturn(false);

        mockMvc.perform(delete("/favorito/1"))
                .andExpect(status().isNotFound());

        verify(favoritoService, times(1)).deleteById(1L);
    }
}
