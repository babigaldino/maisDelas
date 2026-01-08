package com.delas.api.service;

import com.delas.api.model.TokenRedefinicaoSenhaModel;
import com.delas.api.model.UsuarioModel;
import com.delas.api.repository.TokenRedefinicaoSenhaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
public class TokenRedefinicaoSenhaService {

    @Autowired
    private TokenRedefinicaoSenhaRepository tokenRepository;

    // ✅ CORRIGIDO: setId() → setUsuario()
    public TokenRedefinicaoSenhaModel gerarToken(UsuarioModel usuario, String token) {
        TokenRedefinicaoSenhaModel tokenRedefinicao = new TokenRedefinicaoSenhaModel();
        tokenRedefinicao.setToken(token);
        tokenRedefinicao.setUsuario(usuario);  // ✅ CORRIGIDO
        tokenRedefinicao.setDataExpiracao(LocalDateTime.now().plusHours(1));

        TokenRedefinicaoSenhaModel salvo = tokenRepository.save(tokenRedefinicao);
        System.out.println("Token salvo no banco: " + salvo.getToken());
        return salvo;
    }

    public Optional<TokenRedefinicaoSenhaModel> buscarToken(String token) {
        return tokenRepository.findByToken(token);
    }

    public boolean validarToken(String token) {
        Optional<TokenRedefinicaoSenhaModel> tokenOpt = buscarToken(token);
        return tokenOpt.isPresent() && tokenOpt.get().getDataExpiracao()
                .isAfter(LocalDateTime.now());
    }
}
