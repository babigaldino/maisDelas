package com.delas.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServicoDetalhadoDTO {
    private Long id;
    private String titulo;
    private String descricao;
    private String categoria;
    private Double preco;
    private String nomePrestadora;
    private Boolean favorito;
    private String dataCriacao;
}
