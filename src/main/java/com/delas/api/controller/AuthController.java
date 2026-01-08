package com.delas.api.controller;

import com.delas.api.dto.LoginDTO;
import com.delas.api.dto.Request;
import com.delas.api.dto.UsuarioRequestDTO;
import com.delas.api.dto.UsuarioResponseDTO;
import com.delas.api.model.TokenRedefinicaoSenhaModel;
import com.delas.api.model.UsuarioModel;
import com.delas.api.repository.TokenRedefinicaoSenhaRepository;
import com.delas.api.service.EmailService;
import com.delas.api.service.UsuarioService;
import com.delas.api.repository.UsuarioRepository;
import com.delas.api.service.TokenRedefinicaoSenhaService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.delas.api.config.JwtUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private TokenRedefinicaoSenhaService tokenRedefinicaoSenhaService;

    @Autowired
    private TokenRedefinicaoSenhaRepository tokenRedefinicaoSenhaRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private EmailService emailService;

    @GetMapping("/reset-password")
    public ResponseEntity<String> validarToken(@RequestParam("token") String token) {
        try {
            boolean isValid = tokenRedefinicaoSenhaService.validarToken(token);

            if (!isValid) {
                return ResponseEntity.status(400).body("Token inválido ou expirado.");
            }

            return ResponseEntity.ok("Token válido.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao validar o token.");
        }
    }

    @PostMapping("/reset-password-validate")
    public ResponseEntity<?> redefinirSenha(@RequestBody Request request) {
        try {
            String token = request.getToken();
            String novaSenha = request.getNovaSenha();

            // Valida o token
            boolean isValid = tokenRedefinicaoSenhaService.validarToken(token);
            if (!isValid) {
                return ResponseEntity.status(400).body("Token inválido ou expirado.");
            }

            // Busca o ResetToken associado ao token fornecido
            Optional<TokenRedefinicaoSenhaModel> resetTokenOpt = tokenRedefinicaoSenhaRepository.findByToken(token);
            if (resetTokenOpt.isEmpty()) {
                return ResponseEntity.status(400).body("Token inválido.");
            }

            TokenRedefinicaoSenhaModel resetToken = resetTokenOpt.get();
            // ✅ CORRIGIDO: getId() → getUsuario()
            UsuarioModel usuario = resetToken.getUsuario();

            // Criptografa a nova senha
            String senhaCriptografada = passwordEncoder.encode(novaSenha);
            usuario.setSenha(senhaCriptografada);

            // Salva o usuário com a nova senha
            usuarioRepository.save(usuario);

            // Após redefinir a senha, limpa o token
            tokenRedefinicaoSenhaRepository.delete(resetToken);

            return ResponseEntity.ok("Senha redefinida com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao redefinir a senha: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        try {
            UsuarioModel usuario = usuarioRepository.findByEmail(loginDTO.getEmail())
                    .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado."));

            // Verifica se a senha fornecida é compatível com o hash armazenado
            if (!passwordEncoder.matches(loginDTO.getSenha(), usuario.getSenha())) {
                return ResponseEntity.status(401).body("Credenciais inválidas.");
            }

            String token = JwtUtil.generateToken(loginDTO.getEmail());

            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("usuario", UsuarioResponseDTO.fromModel(usuario));
            response.put("message", "Login realizado com sucesso.");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao processar login: " + e.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UsuarioRequestDTO usuarioDTO, BindingResult bindingResult) {
        // ✅ Mostra erros de validação detalhados
        if (bindingResult.hasErrors()) {
            Map<String, String> erros = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> {
                String fieldName = error.getField();
                String errorMessage = error.getDefaultMessage();
                erros.put(fieldName, errorMessage);
            });
            return ResponseEntity.badRequest().body(Map.of(
                    "sucesso", false,
                    "mensagem", "Validação falhou",
                    "erros", erros));
        }

        try {
            UsuarioModel usuario = usuarioService.salvarUsuario(usuarioDTO);
            return ResponseEntity.status(201).body(Map.of(
                    "sucesso", true,
                    "mensagem", "Usuário registrado com sucesso",
                    "usuario", UsuarioResponseDTO.fromModel(usuario)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "sucesso", false,
                    "mensagem", e.getMessage()));
        }
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestParam String email) {
        UsuarioModel usuario = usuarioService.findByEmail(email);
        if (usuario == null) {
            return ResponseEntity.status(404).body("Usuário não encontrado.");
        }

        // Geração do token
        String token = UUID.randomUUID().toString();
        tokenRedefinicaoSenhaService.gerarToken(usuario, token);

        try {
            emailService.sendRecoveryEmail(email, token);
        } catch (MessagingException e) {
            return ResponseEntity.status(500).body("Erro ao enviar e-mail de recuperação.");
        }

        return ResponseEntity.ok("E-mail de recuperação enviado.");
    }
}
