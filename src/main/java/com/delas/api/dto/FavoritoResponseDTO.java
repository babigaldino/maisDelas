package com.delas.api.dto;

import com.delas.api.model.FavoritoModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FavoritoResponseDTO {
    private Long idFavorito;
    private Long servicoId;
    private String servicoTitulo;
    private Long usuarioId;
    private String usuarioNome;
    private LocalDateTime dataFavoritamento;

    public static FavoritoResponseDTO fromModel(FavoritoModel favorito) {
        return new FavoritoResponseDTO(
                favorito.getIdFavorito(),
                favorito.getServicoFavorito().getIdservicos(),
                favorito.getServicoFavorito().getTitulo(),
                favorito.getUsuarioFavorito().getId(),
                favorito.getUsuarioFavorito().getNome(),
                favorito.getDataFavoritamento()
        );
    }
}
