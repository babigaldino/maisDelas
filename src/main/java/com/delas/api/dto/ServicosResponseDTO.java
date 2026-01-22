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
@NoArgsConstructor
public class ServicosResponseDTO {
    private Long idservicos;
    private String descricao;
    private BigDecimal preco;
    private String titulo;
    private LocalDateTime datacriacao;
    private String categoria;
    private Double nota;
    
    
    private UsuarioSimplificadoDTO usuario;
    
   
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UsuarioSimplificadoDTO {
        private Long id;
        private String nome;
        private String email;
        private String tipoUsuario;
    }

    public static ServicosResponseDTO fromModel(ServicosModel servico) {
        ServicosResponseDTO dto = new ServicosResponseDTO();
        dto.setIdservicos(servico.getIdservicos());
        dto.setDescricao(servico.getDescricao());
        dto.setPreco(servico.getPreco());
        dto.setTitulo(servico.getTitulo());
        dto.setDatacriacao(servico.getDatacriacao());
        dto.setCategoria(servico.getCategoria());
        dto.setNota(servico.getNota());
        
       
        if (servico.getUsuario() != null) {
            UsuarioSimplificadoDTO usuarioDTO = new UsuarioSimplificadoDTO();
            usuarioDTO.setId(servico.getUsuario().getId());
            usuarioDTO.setNome(servico.getUsuario().getNome());
            usuarioDTO.setEmail(servico.getUsuario().getEmail());
            usuarioDTO.setTipoUsuario(servico.getUsuario().getTipo().name());
            dto.setUsuario(usuarioDTO);
        }
        
        return dto;
    }
}
