package com.delas.api.dto;

import com.delas.api.model.ContratacaoModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContratacaoResponseDTO {
    private Long idcontratacao;
    private Long usuarioId;
    private String usuarioNome;
    private Long servicoId;
    private String servicoTitulo;
    private String status;
    private LocalDateTime dataContratacao;
    private String comentarios;

    public static ContratacaoResponseDTO fromModel(ContratacaoModel contratacao) {
        return new ContratacaoResponseDTO(
                contratacao.getIdcontratacao(),
                contratacao.getUsuario().getId(),
                contratacao.getUsuario().getNome(),
                contratacao.getServico().getIdservicos(),
                contratacao.getServico().getTitulo(),
                contratacao.getStatus().toString(),
                contratacao.getDataContratacao(),
                contratacao.getComentarios()
        );
    }
}
