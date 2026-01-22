package com.delas.api.dto;

import com.delas.api.model.ServicosModel;
import com.delas.api.model.UsuarioModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    private String cidade;
    private Double latitude;
    private Double longitude;
    private String bio;
    private String foto;
    private LocalDateTime dataCriacao;
    private Double nota;
    
    private List<ServicosModel> servicos = new ArrayList<>();
    private List<Object> avaliacoes = new ArrayList<>();

    public static UsuarioResponseDTO fromModel(UsuarioModel usuario) {
        UsuarioResponseDTO dto = new UsuarioResponseDTO();
        dto.setId(usuario.getId());
        dto.setNome(usuario.getNome());
        dto.setEmail(usuario.getEmail());
        dto.setTelefone(usuario.getTelefone());
        dto.setTipo(usuario.getTipo().toString());
        dto.setBairro(usuario.getBairro());
        dto.setCep(usuario.getCep());
        dto.setRua(usuario.getRua());
        dto.setCpf(usuario.getCpf());
        dto.setCidade(usuario.getCidade());
        dto.setLatitude(usuario.getLatitude());
        dto.setLongitude(usuario.getLongitude());
        dto.setBio(usuario.getBio());
        dto.setFoto(usuario.getFoto());
        dto.setDataCriacao(usuario.getDataCriacao());
        dto.setNota(usuario.getNota() != null ? usuario.getNota() : 0.0);
        
        return dto;
    }
}
