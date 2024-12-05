package com.delas.api.controller;

import com.delas.api.dto.LoginDTO;
import com.delas.api.dto.Request;
import com.delas.api.model.TokenRedefinicaoSenhaModel;
import com.delas.api.model.UsuarioModel;
import com.delas.api.repository.TokenRedefinicaoSenhaRepository;
import com.delas.api.service.EmailService;
import com.delas.api.service.UsuarioService;
import com.delas.api.repository.UsuarioRepository;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import com.delas.api.service.TokenRedefinicaoSenhaService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
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



    @GetMapping("/reset-password")
    public ResponseEntity<String> validarToken(@RequestParam("token") String token) {
        try {
            // Usando o método validarToken do service
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
            UsuarioModel usuario = resetToken.getId();  // Aqui você deve pegar o usuário associado ao token

            // Criptografa a nova senha
            String senhaCriptografada = passwordEncoder.encode(novaSenha);
            usuario.setSenha(senhaCriptografada);

            // Salva o usuário com a nova senha
            usuarioRepository.save(usuario);

            // Após redefinir a senha, você pode limpar o token
            tokenRedefinicaoSenhaRepository.delete(resetToken); // Opcional, dependendo do seu fluxo

            return ResponseEntity.ok("Senha redefinida com sucesso.");
//        } catch (Exception e) {
//            return ResponseEntity.status(500).body("Erro ao redefinir a senha.");
//        }

        } finally {

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
            response.put("message", "Login realizado com sucesso.");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao processar login: " + e.getMessage());
        }
    }


    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UsuarioModel usuario) {
        try {
            usuarioService.salvarUsuario(usuario);
            return ResponseEntity.ok("Usuário registrado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private EmailService emailService;

    private void sendPasswordResetEmail(String email, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("maisdelas77@gmail.com");
        message.setTo(email);
        message.setSubject("Recuperação de Senha");
        message.setText("Clique no link abaixo para redefinir sua senha:\n" +
                "http://seusite.com/reset-password?token=" + token);

        mailSender.send(message);
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
