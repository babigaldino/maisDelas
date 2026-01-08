package com.delas.api.dto;

import com.delas.api.model.ServicosModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ServicosResponseDTO {
    private Long idservicos;
    private String descricao;
    private BigDecimal preco;
    private String titulo;
    private LocalDateTime datacriacao;
    private String categoria;
    private Long usuarioId;
    private String usuarioNome;
    private Double nota;

    public static ServicosResponseDTO fromModel(ServicosModel servico) {
        return new ServicosResponseDTO(
                servico.getIdservicos(),
                servico.getDescricao(),
                servico.getPreco(),
                servico.getTitulo(),
                servico.getDatacriacao(),
                servico.getCategoria(),
                servico.getUsuario().getId(),
                servico.getUsuario().getNome(),
                servico.getNota()
        );
    }
}
