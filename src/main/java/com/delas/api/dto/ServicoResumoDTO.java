package com.delas.api.dto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServicoResumoDTO {
    private Long id;
    private String titulo;
    private String categoria;
    private Double preco;
    private Double avaliacaoMedia;
}
