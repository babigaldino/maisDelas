package com.delas.api.dto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor


public class UsuarioDTO {

    @NotBlank(message = "O nome não pode ser vazio")
    private String nome;

    @NotBlank(message = "O email não pode ser vazio")
    @Email(message = "Email inválido")
    private String email;

    @NotBlank(message = "A senha não pode ser vazia")
    private String senha;

    private String telefone;
    private String tipo;  // Caso seja um Enum ou String, vai depender da implementação
    private String rua;
    private String bairro;
    private String cep;
    private String cpf;
    public enum TipoUsuario {
        ADMIN,
        CLIENTE,
        PRESTADOR
    }
}
