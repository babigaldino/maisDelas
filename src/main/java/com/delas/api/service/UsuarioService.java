package com.delas.api.service;

import com.delas.api.model.UsuarioModel;
import com.delas.api.repository.UsuarioRepository;
import com.delas.api.repository.TokenRedefinicaoSenhaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private TokenRedefinicaoSenhaRepository tokenRepository;


    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Método para validar o CPF
    public boolean cpfValido(String cpf) {
        // Verifica se o CPF é nulo, possui tamanho diferente de 11 ou contém caracteres não numéricos
        if (cpf == null || cpf.length() != 11 || !cpf.matches("\\d+")) {
            return false;
        }

        // Cálculo dos dígitos verificadores
        int soma1 = 0, soma2 = 0;
        for (int i = 0; i < 9; i++) {
            int digito = Character.getNumericValue(cpf.charAt(i));
            soma1 += digito * (10 - i);
            soma2 += digito * (11 - i);
        }

        int verificador1 = (soma1 * 10) % 11;
        verificador1 = (verificador1 == 10) ? 0 : verificador1;

        int verificador2 = ((soma2 + (verificador1 * 2)) * 10) % 11;
        verificador2 = (verificador2 == 10) ? 0 : verificador2;

        // Verifica se os dígitos verificadores coincidem com os últimos dois dígitos do CPF
        return verificador1 == Character.getNumericValue(cpf.charAt(9)) &&
                verificador2 == Character.getNumericValue(cpf.charAt(10));
    }

    // Método para salvar um novo usuário
    public UsuarioModel salvarUsuario(UsuarioModel usuario) {
        if (!cpfValido(usuario.getCpf())) {
            throw new IllegalArgumentException("CPF inválido!"); // metodo para chamar o usuario//
        }
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        return usuarioRepository.save(usuario);
    }

    // Método para listar todos os usuários
    public List<UsuarioModel> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    // Método para buscar um usuário pelo ID
    public Optional<UsuarioModel> buscarUsuarioPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    // Método para deletar um usuário pelo ID
    public boolean deletarUsuario(Long id) {
        usuarioRepository.deleteById(id);
        return true;
    }



    // Buscar usuário por email
    public UsuarioModel findByEmail(String email) {
        return usuarioRepository.findByEmail(email).orElse(null);
    }




    // Regras de negócio para edição de perfil
    public UsuarioModel atualizarUsuario(Long id, UsuarioModel usuarioAtualizado) {
        return usuarioRepository.findById(id).map(usuario -> {
            usuario.setNome(usuarioAtualizado.getNome());
            usuario.setEmail(usuarioAtualizado.getEmail());
            usuario.setTelefone(usuarioAtualizado.getTelefone());
            usuario.setTipo(usuarioAtualizado.getTipo());
            usuario.setRua(usuarioAtualizado.getRua());
            usuario.setBairro(usuarioAtualizado.getBairro());
            usuario.setCep(usuarioAtualizado.getCep());
            // Criptografar a senha se ela foi atualizada
            if (usuarioAtualizado.getSenha() != null && !usuarioAtualizado.getSenha().isEmpty()) {
                usuario.setSenha(passwordEncoder.encode(usuarioAtualizado.getSenha()));
            }
            return usuarioRepository.save(usuario);
        }).orElseThrow(() -> new RuntimeException("Usuário não encontrado com o ID: " + id));
    }

}




