package com.delas.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContratacaoDTO {
    private Long id;
    private String tituloServico;
    private String nomePrestadora;
    private String status;
    private String dataContratacao;
    private Double preco;
}
