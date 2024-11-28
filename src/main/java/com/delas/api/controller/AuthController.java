package com.delas.api.controller;
import com.delas.api.dto.LoginDTO;
import com.delas.api.model.UsuarioModel;
import com.delas.api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.delas.api.config.JwtUtil;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public String login(@RequestBody LoginDTO loginDTO) {
        UsuarioModel usuario = usuarioRepository.findByEmail(loginDTO.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Verificar a senha
        if (passwordEncoder.matches(loginDTO.getSenha(), usuario.getSenha())) {
            return jwtUtil.generateToken(usuario.getEmail());
        } else {
            throw new RuntimeException("Senha inválida");
        }
    }
}
