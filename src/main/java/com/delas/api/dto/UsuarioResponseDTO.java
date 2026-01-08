package com.delas.api.dto;

import com.delas.api.model.UsuarioModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioResponseDTO {
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String tipo;
    private String bairro;
    private String cep;
    private String rua;
    private String cpf;
    private LocalDateTime dataCriacao;

    // Mapper manual
    public static UsuarioResponseDTO fromModel(UsuarioModel usuario) {
        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getTelefone(),
                usuario.getTipo().toString(),
                usuario.getBairro(),
                usuario.getCep(),
                usuario.getRua(),
                usuario.getCpf(),
                usuario.getDataCriacao()
        );
    }
}
