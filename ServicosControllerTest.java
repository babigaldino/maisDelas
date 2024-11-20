package com.delas.api.controller;

import com.delas.api.model.ServicosModel;
import com.delas.api.service.ServicosService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ServicosControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ServicosService servicosService;

    @InjectMocks
    private ServicosController servicosController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(servicosController).build();
    }

    @Test
    @DisplayName("Deve criar um novo serviço")
    void createServico() throws Exception {
        ServicosModel servico = new ServicosModel();
        servico.setId(1L);
        servico.setNome("Serviço Teste");

        when(servicosService.save(any(ServicosModel.class))).thenReturn(servico);

        mockMvc.perform(post("/servicos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\": \"Serviço Teste\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Serviço Teste"));

        verify(servicosService, times(1)).save(any(ServicosModel.class));
    }

    @Test
    @DisplayName("Deve retornar todos os serviços")
    void getAllServicos() throws Exception {
        mockMvc.perform(get("/servicos"))
                .andExpect(status().isNoContent());

        verify(servicosService, times(1)).findAll();
    }

    @Test
    @DisplayName("Deve retornar um serviço específico por ID")
    void getServicoById() throws Exception {
        ServicosModel servico = new ServicosModel();
        servico.setId(1L);
        servico.setNome("Serviço Teste");

        when(servicosService.findById(1L)).thenReturn(Optional.of(servico));

        mockMvc.perform(get("/servicos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Serviço Teste"));

        verify(servicosService, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Deve retornar 404 se o serviço não for encontrado pelo ID")
    void getServicoByIdNotFound() throws Exception {
        when(servicosService.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/servicos/1"))
                .andExpect(status().isNotFound());

        verify(servicosService, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Deve atualizar um serviço existente")
    void updateServico() throws Exception {
        ServicosModel servico = new ServicosModel();
        servico.setId(1L);
        servico.setNome("Serviço Teste");

        ServicosModel updatedServico = new ServicosModel();
        updatedServico.setId(1L);
        updatedServico.setNome("Serviço Atualizado");

        when(servicosService.findById(1L)).thenReturn(Optional.of(servico));
        when(servicosService.update(eq(1L), any(ServicosModel.class))).thenReturn(Optional.of(updatedServico));

        mockMvc.perform(put("/servicos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\": \"Serviço Atualizado\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Serviço Atualizado"));

        verify(servicosService, times(1)).update(eq(1L), any(ServicosModel.class));
    }

    @Test
    @DisplayName("Deve retornar 404 ao tentar atualizar um serviço inexistente")
    void updateServicoNotFound() throws Exception {
        when(servicosService.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(put("/servicos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\": \"Serviço Atualizado\"}"))
                .andExpect(status().isNotFound());

        verify(servicosService, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Deve excluir um serviço")
    void deleteServico() throws Exception {
        when(servicosService.deleteById(1L)).thenReturn(true);

        mockMvc.perform(delete("/servicos/1"))
                .andExpect(status().isNoContent());

        verify(servicosService, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Deve retornar 404 se o serviço não for encontrado ao tentar excluir")
    void deleteServicoNotFound() throws Exception {
        when(servicosService.deleteById(1L)).thenReturn(false);

        mockMvc.perform(delete("/servicos/1"))
                .andExpect(status().isNotFound());

        verify(servicosService, times(1)).deleteById(1L);
    }
}
