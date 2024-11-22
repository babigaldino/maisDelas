//package com.delas.api.controller;
//
//import com.delas.api.model.AvaliacaoModel;
//import com.delas.api.service.AvaliacaoService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.time.LocalDateTime;
//import java.util.Optional;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@WebMvcTest(AvaliacaoController.class)
//
//class AvaliacaoControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private AvaliacaoService avaliacaoService;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Test
//    @DisplayName("Deve criar uma nova avaliação com sucesso")
//    void criarAvaliacao() throws Exception {
//        AvaliacaoModel avaliacao = new AvaliacaoModel(
//                null, 5, "Excelente serviço", LocalDateTime.now(), null, null
//        );
//
//        AvaliacaoModel avaliacaoSalva = new AvaliacaoModel(
//                1L, 5, "Excelente serviço", LocalDateTime.now(), null, null
//        );
//
//        Mockito.when(avaliacaoService.salvar(any(AvaliacaoModel.class))).thenReturn(avaliacaoSalva);
//
//        mockMvc.perform(post("/avaliacoes")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(avaliacao)))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.idavaliacao").value(1L))
//                .andExpect(jsonPath("$.nota").value(5))
//                .andExpect(jsonPath("$.comentario").value("Excelente serviço"));
//    }
//
//    @Test
//    @DisplayName("Deve buscar uma avaliação pelo ID")
//    void buscarAvaliacaoPorId() throws Exception {
//        AvaliacaoModel avaliacao = new AvaliacaoModel(
//                1L, 4, "Muito bom", LocalDateTime.now(), null, null
//        );
//
//        Mockito.when(avaliacaoService.buscarPorId(1L)).thenReturn(Optional.of(avaliacao));
//
//        mockMvc.perform(get("/avaliacoes/1"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.idavaliacao").value(1L))
//                .andExpect(jsonPath("$.nota").value(4))
//                .andExpect(jsonPath("$.comentario").value("Muito bom"));
//    }
//
//    @Test
//    @DisplayName("Deve atualizar uma avaliação existente")
//    void atualizarAvaliacao() throws Exception {
//        AvaliacaoModel avaliacaoAtualizada = new AvaliacaoModel(
//                1L, 3, "Serviço satisfatório", LocalDateTime.now(), null, null
//        );
//
//        Mockito.when(avaliacaoService.atualizar(eq(1L), any(AvaliacaoModel.class))).thenReturn(Optional.of(avaliacaoAtualizada));
//
//        mockMvc.perform(put("/avaliacoes/1")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(avaliacaoAtualizada)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.idavaliacao").value(1L))
//                .andExpect(jsonPath("$.nota").value(3))
//                .andExpect(jsonPath("$.comentario").value("Serviço satisfatório"));
//    }
//
//    @Test
//    @DisplayName("Deve deletar uma avaliação pelo ID")
//    void deletarAvaliacao() throws Exception {
//        Mockito.doNothing().when(avaliacaoService).deletarPorId(1L);
//
//        mockMvc.perform(delete("/avaliacoes/1"))
//                .andExpect(status().isNoContent());
//    }
//}
