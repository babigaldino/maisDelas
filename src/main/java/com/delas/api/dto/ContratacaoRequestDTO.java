package com.delas.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContratacaoRequestDTO {
    @NotNull(message = "ID do usuário é obrigatório")
    private Long usuarioId;

    @NotNull(message = "ID do serviço é obrigatório")
    private Long servicoId;

    @NotBlank(message = "Status é obrigatório")
    private String status; // AGUARDANDO_RESPOSTA, ACEITA, REJEITADA, FINALIZADA, CANCELADA

    private String comentarios;
}
