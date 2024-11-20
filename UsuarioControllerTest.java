package com.delas.api.controller;

import com.delas.api.model.UsuarioModel;
import com.delas.api.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UsuarioControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private UsuarioController usuarioController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(usuarioController).build();
    }

    @Test
    void testCreateUsuario() throws Exception {
        UsuarioModel usuario = new UsuarioModel();
        usuario.setId(1L);
        usuario.setNome("Test User");
        usuario.setEmail("testuser@example.com");

        when(usuarioService.salvarUsuario(any(UsuarioModel.class))).thenReturn(usuario);

        mockMvc.perform(post("/usuario")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\":\"Test User\",\"email\":\"testuser@example.com\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("Test User"))
                .andExpect(jsonPath("$.email").value("testuser@example.com"));
    }

    @Test
    void testGetAllUsuarios() throws Exception {
        UsuarioModel usuario1 = new UsuarioModel();
        usuario1.setId(1L);
        usuario1.setNome("Test User 1");

        UsuarioModel usuario2 = new UsuarioModel();
        usuario2.setId(2L);
        usuario2.setNome("Test User 2");

        when(usuarioService.listarUsuarios()).thenReturn(Arrays.asList(usuario1, usuario2));

        mockMvc.perform(get("/usuario"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Test User 1"))
                .andExpect(jsonPath("$[1].nome").value("Test User 2"));
    }

    @Test
    void testGetUsuarioById_Found() throws Exception {
        UsuarioModel usuario = new UsuarioModel();
        usuario.setId(1L);
        usuario.setNome("Test User");

        when(usuarioService.buscarUsuarioPorId(1L)).thenReturn(Optional.of(usuario));

        mockMvc.perform(get("/usuario/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Test User"));
    }

    @Test
    void testGetUsuarioById_NotFound() throws Exception {
        when(usuarioService.buscarUsuarioPorId(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/usuario/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdateUsuario() throws Exception {
        UsuarioModel existingUsuario = new UsuarioModel();
        existingUsuario.setId(1L);
        existingUsuario.setNome("Old Name");

        UsuarioModel updatedUsuario = new UsuarioModel();
        updatedUsuario.setId(1L);
        updatedUsuario.setNome("Updated Name");

        when(usuarioService.atualizarUsuario(eq(1L), any(UsuarioModel.class))).thenReturn(updatedUsuario);

        mockMvc.perform(put("/usuario/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\":\"Updated Name\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Updated Name"));
    }

    @Test
    void testDeleteUsuario_Found() throws Exception {
        when(usuarioService.deletarUsuario(1L)).thenReturn(true);

        mockMvc.perform(delete("/usuario/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteUsuario_NotFound() throws Exception {
        when(usuarioService.deletarUsuario(1L)).thenReturn(false);

        mockMvc.perform(delete("/usuario/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testTestEndpoint() throws Exception {
        mockMvc.perform(get("/usuario/test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("API funcionando amém"));
    }
}
