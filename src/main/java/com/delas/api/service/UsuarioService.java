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

    public void redefinirSenha(UsuarioModel usuario, String novaSenha) {
        String senhaCriptografada = passwordEncoder.encode(novaSenha);
        usuario.setSenha(senhaCriptografada);
        usuario.setResetToken(null);
        usuarioRepository.save(usuario);
    }

    public UsuarioModel salvarUsuario(UsuarioModel usuario) {
        if (!cpfValido(usuario.getCpf())) {
            throw new IllegalArgumentException("CPF inválido!"); // metodo para chamar o usuario//
        }
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        return usuarioRepository.save(usuario);
    }

    public List<UsuarioModel> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public Optional<UsuarioModel> buscarUsuarioPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public UsuarioModel atualizarUsuario(Long id, UsuarioModel usuarioAtualizado) {
        return usuarioRepository.findById(id).map(usuario -> {
            if (!cpfValido(usuarioAtualizado.getCpf())) {
                throw new IllegalArgumentException("CPF inválido!");
            }
            usuario.setNome(usuarioAtualizado.getNome());
            usuario.setEmail(usuarioAtualizado.getEmail());
            usuario.setTelefone(usuarioAtualizado.getTelefone());
            usuario.setTipo(usuarioAtualizado.getTipo());
            usuario.setRua(usuarioAtualizado.getRua());
            usuario.setBairro(usuarioAtualizado.getBairro());
            usuario.setCep(usuarioAtualizado.getCep());
            if (usuarioAtualizado.getSenha() != null && !usuarioAtualizado.getSenha().isEmpty()) {
                usuario.setSenha(passwordEncoder.encode(usuarioAtualizado.getSenha()));
            }
            return usuarioRepository.save(usuario);
        }).orElseThrow(() -> new RuntimeException("Usuário não encontrado com o ID: " + id));
    }

    public boolean deletarUsuario(Long id) {
        usuarioRepository.deleteById(id);
        return true;
    }

    public UsuarioModel findByEmail(String email) {
        return usuarioRepository.findByEmail(email).orElse(null);
    }

    public UsuarioModel criarUsuario(UsuarioDTO usuarioDTO) {
        if (!cpfValido(usuarioDTO.getCpf())) {
            throw new IllegalArgumentException("CPF inválido!");
        }
        UsuarioModel usuario = new UsuarioModel();
        usuario.setNome(usuarioDTO.getNome());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setTelefone(usuarioDTO.getTelefone());
        usuario.setTipo(UsuarioModel.TipoUsuario.valueOf(usuarioDTO.getTipo()));
        usuario.setRua(usuarioDTO.getRua());
        usuario.setBairro(usuarioDTO.getBairro());
        usuario.setCep(usuarioDTO.getCep());
        usuario.setCpf(usuarioDTO.getCpf());
        usuario.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
        return usuarioRepository.save(usuario);
    }

    public Optional<UsuarioModel> findByResetToken(String token) {
        return usuarioRepository.findByResetToken(token);
    }

    public void atualizarResetToken(String token, String email) {
        UsuarioModel usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com o email: " + email));
        usuario.setResetToken(token);
        usuarioRepository.save(usuario);
    }

    public String obterRankingPrestador(Long prestadorId) {
        UsuarioModel prestador = usuarioRepository.findById(prestadorId)
                .orElseThrow(() -> new RuntimeException("Prestador não encontrado com o ID: " + prestadorId));

        if (prestador.getTipo() != UsuarioModel.TipoUsuario.PRESTADOR) {
            throw new RuntimeException("O usuário não é um prestador.");
        }

        return prestador.determinarRanking();
    }

    public UsuarioModel editarPerfil(Long id, UsuarioModel usuarioAtualizado) {
        UsuarioModel usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com o ID: " + id));

        if (!usuarioAtualizado.getEmail().equals(usuarioExistente.getEmail())) {
            if (usuarioRepository.existsByEmail(usuarioAtualizado.getEmail())) {
                throw new IllegalArgumentException("E-mail já está em uso por outro usuário.");
            }
        }

        if (!usuarioAtualizado.getCpf().equals(usuarioExistente.getCpf())) {
            if (usuarioRepository.existsByCpf(usuarioAtualizado.getCpf())) {
                throw new IllegalArgumentException("CPF já está em uso por outro usuário.");
            }
        }

        if (!cpfValido(usuarioAtualizado.getCpf())) {
            throw new IllegalArgumentException("CPF inválido!");
        }

        usuarioExistente.setNome(usuarioAtualizado.getNome());
        usuarioExistente.setEmail(usuarioAtualizado.getEmail());
        usuarioExistente.setTelefone(usuarioAtualizado.getTelefone());
        usuarioExistente.setRua(usuarioAtualizado.getRua());
        usuarioExistente.setBairro(usuarioAtualizado.getBairro());
        usuarioExistente.setCep(usuarioAtualizado.getCep());

        if (usuarioAtualizado.getSenha() != null && !usuarioAtualizado.getSenha().isEmpty()) {
            usuarioExistente.setSenha(passwordEncoder.encode(usuarioAtualizado.getSenha()));
        }

        return usuarioRepository.save(usuarioExistente);
    }
}
