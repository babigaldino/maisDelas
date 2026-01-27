package com.delas.api.service;

import com.delas.api.dto.UsuarioRequestDTO;
import com.delas.api.dto.UsuarioUpdateDTO; // ✅ ADICIONE
import com.delas.api.model.UsuarioModel;
import com.delas.api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // ✅ CORRIGIDO: Valida CPF
    public boolean cpfValido(String cpf) {
        if (cpf == null || cpf.length() != 11 || !cpf.matches("\\d+")) {
            return false;
        }

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

        return verificador1 == Character.getNumericValue(cpf.charAt(9)) &&
                verificador2 == Character.getNumericValue(cpf.charAt(10));
    }

    public UsuarioModel salvarUsuario(UsuarioRequestDTO usuarioDTO) {
        // if (!cpfValido(usuarioDTO.getCpf())) {
        //     throw new IllegalArgumentException("CPF inválido!");
        // }

        // Validação de usuário duplicado
        if (usuarioRepository.existsByEmail(usuarioDTO.getEmail())) {
            throw new RuntimeException("Email já cadastrado");
        }

        if (usuarioRepository.existsByCpf(usuarioDTO.getCpf())) {
            throw new RuntimeException("CPF já cadastrado");
        }

        UsuarioModel usuario = new UsuarioModel();
        usuario.setNome(usuarioDTO.getNome());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
        usuario.setTelefone(usuarioDTO.getTelefone());
        usuario.setTipo(UsuarioModel.TipoUsuario.valueOf(usuarioDTO.getTipo()));
        usuario.setBairro(usuarioDTO.getBairro());
        usuario.setCep(usuarioDTO.getCep());
        usuario.setRua(usuarioDTO.getRua());
        usuario.setCpf(usuarioDTO.getCpf());
        usuario.setCidade(usuarioDTO.getCidade() != null ? usuarioDTO.getCidade() : "Recife");
        usuario.setLatitude(usuarioDTO.getLatitude());
        usuario.setLongitude(usuarioDTO.getLongitude());

        return usuarioRepository.save(usuario);
    }

    // ✅ NOVO MÉTODO: Atualizar perfil (sem senha e tipo)
    public UsuarioModel atualizarPerfil(Long id, UsuarioUpdateDTO dto) {
        UsuarioModel usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setTelefone(dto.getTelefone());
        usuario.setRua(dto.getRua());
        usuario.setBairro(dto.getBairro());
        usuario.setCep(dto.getCep());
        
        // ✅ Atualiza bio se não for null
        if (dto.getBio() != null) {
            usuario.setBio(dto.getBio());
        }

        return usuarioRepository.save(usuario);
    }

    // ✅ MÉTODO ANTIGO: Mantém para outros usos (se necessário)
    public UsuarioModel atualizarUsuario(Long id, UsuarioRequestDTO usuarioDTO) {
        UsuarioModel usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        usuario.setNome(usuarioDTO.getNome());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setTelefone(usuarioDTO.getTelefone());
        usuario.setTipo(UsuarioModel.TipoUsuario.valueOf(usuarioDTO.getTipo()));
        usuario.setBairro(usuarioDTO.getBairro());
        usuario.setCep(usuarioDTO.getCep());
        usuario.setRua(usuarioDTO.getRua());
        usuario.setCpf(usuarioDTO.getCpf());

        // ✨ NOVOS CAMPOS
        if (usuarioDTO.getCidade() != null) {
            usuario.setCidade(usuarioDTO.getCidade());
        }
        if (usuarioDTO.getLatitude() != null) {
            usuario.setLatitude(usuarioDTO.getLatitude());
        }
        if (usuarioDTO.getLongitude() != null) {
            usuario.setLongitude(usuarioDTO.getLongitude());
        }

        // Atualiza senha apenas se foi fornecida
        if (usuarioDTO.getSenha() != null && !usuarioDTO.getSenha().isEmpty()) {
            usuario.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
        }

        return usuarioRepository.save(usuario);
    }

    public List<UsuarioModel> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public Optional<UsuarioModel> buscarUsuarioPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public void deletarUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuário não encontrado");
        }
        usuarioRepository.deleteById(id);
    }

    public UsuarioModel findByEmail(String email) {
        return usuarioRepository.findByEmail(email).orElse(null);
    }
}
