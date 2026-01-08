package com.delas.api.dto;

import com.delas.api.model.AvaliacaoModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AvaliacaoResponseDTO {
    private Long idavaliacao;
    private Long contratacaoId;
    private Double nota;
    private String comentario;
    private LocalDateTime dataAvaliacao;

    public static AvaliacaoResponseDTO fromModel(AvaliacaoModel avaliacao) {
        return new AvaliacaoResponseDTO(
                avaliacao.getIdavaliacao(),
                avaliacao.getContratacao().getIdcontratacao(),
                avaliacao.getNota(),
                avaliacao.getComentario(),
                avaliacao.getDataAvaliacao()
        );
    }
}
