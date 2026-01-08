package com.delas.api.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ServicosRequestDTO {
    @NotBlank(message = "Descrição é obrigatória")
    private String descricao;

    @NotNull(message = "Preço é obrigatório")
    @DecimalMin(value = "0.0", inclusive = false, message = "Preço deve ser maior que zero")
    private BigDecimal preco;

    @NotBlank(message = "Título é obrigatório")
    @Size(min = 5, max = 70, message = "Título deve ter entre 5 e 70 caracteres")
    private String titulo;

    @NotBlank(message = "Categoria é obrigatória")
    private String categoria;
}
