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
    private Long id;
    private String status;
    private String comentarios;
    private LocalDateTime dataContratacao; 
    
    
    private Long clienteId;
    private String clienteNome;
    
    // Dados do serviço
    private Long servicoId;
    private String servicoTitulo;
    private String servicoDescricao;
    private Double servicoPreco;
    
    // Dados da prestadora
    private Long prestadoraId;
    private String prestadoraNome;
    
    public static ContratacaoResponseDTO fromModel(ContratacaoModel contratacao) {
        ContratacaoResponseDTO dto = new ContratacaoResponseDTO();
        dto.setId(contratacao.getIdcontratacao());
        dto.setStatus(contratacao.getStatus().toString());
        dto.setComentarios(contratacao.getComentarios());
        dto.setDataContratacao(contratacao.getDataContratacao()); 
        
        
        if (contratacao.getUsuario() != null) {
            dto.setClienteId(contratacao.getUsuario().getId());
            dto.setClienteNome(contratacao.getUsuario().getNome());
        }
        
        if (contratacao.getServico() != null) {
            dto.setServicoId(contratacao.getServico().getIdservicos());
            dto.setServicoTitulo(contratacao.getServico().getTitulo());
            dto.setServicoDescricao(contratacao.getServico().getDescricao());
            dto.setServicoPreco(contratacao.getServico().getPreco().doubleValue());
            
            if (contratacao.getServico().getUsuario() != null) {
                dto.setPrestadoraId(contratacao.getServico().getUsuario().getId());
                dto.setPrestadoraNome(contratacao.getServico().getUsuario().getNome());
            }
        }
        
        return dto;
    }
}
