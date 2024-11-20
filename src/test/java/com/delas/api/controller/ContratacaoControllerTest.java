package com.delas.api.controller;

import com.delas.api.model.ContratacaoModel;
import com.delas.api.repository.ContratacaoRepository;
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

class ContratacaoControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ContratacaoRepository contratacaoRepository;

    @InjectMocks
    private ContratacaoController contratacaoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(contratacaoController).build();
    }

    @Test
    @DisplayName("Deve criar uma nova contratação")
    void createContratacao() throws Exception {
        ContratacaoModel contratacao = new ContratacaoModel();
        contratacao.setId(1L);
        contratacao.setCampoExemplo("Comentário de exemplo");

        when(contratacaoRepository.save(any(ContratacaoModel.class))).thenReturn(contratacao);

        mockMvc.perform(post("/contratacao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"campoExemplo\": \"Comentário de exemplo\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.campoExemplo").value("Comentário de exemplo"));

        verify(contratacaoRepository, times(1)).save(any(ContratacaoModel.class));
    }

    @Test
    @DisplayName("Deve retornar todas as contratações")
    void getAllContratacoes() throws Exception {
        mockMvc.perform(get("/contratacao"))
                .andExpect(status().isOk());

        verify(contratacaoRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Deve retornar uma contratação pelo ID")
    void getContratacaoById() throws Exception {
        ContratacaoModel contratacao = new ContratacaoModel();
        contratacao.setId(1L);
        contratacao.setCampoExemplo("Comentário de exemplo");

        when(contratacaoRepository.findById(1L)).thenReturn(java.util.Optional.of(contratacao));

        mockMvc.perform(get("/contratacao/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.campoExemplo").value("Comentário de exemplo"));

        verify(contratacaoRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Deve retornar 404 se contratação não for encontrada pelo ID")
    void getContratacaoByIdNotFound() throws Exception {
        when(contratacaoRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        mockMvc.perform(get("/contratacao/1"))
                .andExpect(status().isNotFound());

        verify(contratacaoRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Deve atualizar uma contratação existente")
    void updateContratacao() throws Exception {
        ContratacaoModel contratacao = new ContratacaoModel();
        contratacao.setId(1L);
        contratacao.setCampoExemplo("Comentário de exemplo");

        ContratacaoModel updatedContratacao = new ContratacaoModel();
        updatedContratacao.setId(1L);
        updatedContratacao.setCampoExemplo("Comentário atualizado");

        when(contratacaoRepository.findById(1L)).thenReturn(java.util.Optional.of(contratacao));
        when(contratacaoRepository.save(any(ContratacaoModel.class))).thenReturn(updatedContratacao);

        mockMvc.perform(put("/contratacao/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"campoExemplo\": \"Comentário atualizado\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.campoExemplo").value("Comentário atualizado"));

        verify(contratacaoRepository, times(1)).save(any(ContratacaoModel.class));
    }

    @Test
    @DisplayName("Deve excluir uma contratação pelo ID")
    void deleteContratacao() throws Exception {
        doNothing().when(contratacaoRepository).deleteById(1L);

        mockMvc.perform(delete("/contratacao/1"))
                .andExpect(status().isNoContent());

        verify(contratacaoRepository, times(1)).deleteById(1L);
    }
}
