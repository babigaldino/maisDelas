package com.delas.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioPublicoDTO {
    private String nome;
    private String email;
    private String telefone;
    private Boolean perfilCompleto;
}
