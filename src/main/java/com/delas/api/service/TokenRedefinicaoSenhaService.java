package com.delas.api.service;

import com.delas.api.model.TokenRedefinicaoSenhaModel;
import com.delas.api.model.UsuarioModel;
import com.delas.api.repository.TokenRedefinicaoSenhaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TokenRedefinicaoSenhaService {

    private final TokenRedefinicaoSenhaRepository tokenRepository;

    @Autowired
    public TokenRedefinicaoSenhaService(TokenRedefinicaoSenhaRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    // Método para gerar e salvar o token de redefinição de senha
    public TokenRedefinicaoSenhaModel gerarToken(UsuarioModel usuario, String token) {
        TokenRedefinicaoSenhaModel tokenRedefinicao = new TokenRedefinicaoSenhaModel();
        tokenRedefinicao.setToken(token);
        tokenRedefinicao.setUsuario(usuario);
        tokenRedefinicao.setDataExpiracao(LocalDateTime.now().plusHours(1));  // Expira em 1 hora

        return tokenRepository.save(tokenRedefinicao);
    }

    // Método para validar o token
    public boolean validarToken(String token) {
        Optional<TokenRedefinicaoSenhaModel> tokenOpt = tokenRepository.findByToken(token);
        if (tokenOpt.isPresent()) {
            TokenRedefinicaoSenhaModel tokenRedefinicao = tokenOpt.get();
            return tokenRedefinicao.getDataExpiracao().isAfter(LocalDateTime.now());
        }
        return false;
    }

    // Método para remover o token após a redefinição da senha
    public void removerToken(TokenRedefinicaoSenhaModel tokenRedefinicao) {
        tokenRepository.delete(tokenRedefinicao);


    }
}
