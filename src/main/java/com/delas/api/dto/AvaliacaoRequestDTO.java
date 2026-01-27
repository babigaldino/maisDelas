package com.delas.api.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AvaliacaoRequestDTO {

    @NotNull(message = "ID da contratação é obrigatório")
    private Long contratacaoId;

    @NotNull(message = "Nota é obrigatória")
    @DecimalMin(value = "1.0", message = "Nota deve ser no mínimo 1")
    @DecimalMax(value = "5.0", message = "Nota deve ser no máximo 5")
    private BigDecimal nota;

    private String comentario;
}
