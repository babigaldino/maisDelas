package com.delas.api.service;

import com.delas.api.dto.UsuarioDTO;
import com.delas.api.model.UsuarioModel;
import com.delas.api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Método para salvar um novo usuário
    public UsuarioModel salvarUsuario(UsuarioModel usuario) {
        // Criptografia da senha antes de salvar
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

    // Método para atualizar um usuário existente
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

    // Método para deletar um usuário pelo ID
    public boolean deletarUsuario(Long id) {
        usuarioRepository.deleteById(id);
        return true;
    }

    // Buscar usuário por email
    public UsuarioModel findByEmail(String email) {
        return usuarioRepository.findByEmail(email).orElse(null);
    }

    // Método para criar um usuário com dados do DTO
    public UsuarioModel criarUsuario(UsuarioDTO usuarioDTO) {
        UsuarioModel usuario = new UsuarioModel();
        usuario.setNome(usuarioDTO.getNome());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setTelefone(usuarioDTO.getTelefone());
        usuario.setTipo(UsuarioModel.TipoUsuario.valueOf(usuarioDTO.getTipo()));
        usuario.setRua(usuarioDTO.getRua());
        usuario.setBairro(usuarioDTO.getBairro());
        usuario.setCep(usuarioDTO.getCep());
        usuario.setCpf(usuarioDTO.getCpf());

        // Criptografia da senha
        usuario.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));

        return usuarioRepository.save(usuario);
    }
}
