package com.delas.api.controller;

import com.delas.api.dto.UsuarioDTO;
import com.delas.api.model.UsuarioModel;
import com.delas.api.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // Criar usuário - utilizando UsuarioDTO
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioModel criarUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        // Criação do usuário chamando o serviço
        return usuarioService.criarUsuario(usuarioDTO);
    }

    // Obter todos os usuários
    @GetMapping
    public List<UsuarioModel> getAllUsuarios() {
        return usuarioService.listarUsuarios();
    }

    // Obter usuário por ID
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioModel> getUsuarioById(@PathVariable Long id) {
        return usuarioService.buscarUsuarioPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(404).build());
    }

    // Atualizar um usuário existente
    @PutMapping("/{id}")
    public UsuarioModel updateUsuario(@PathVariable Long id, @RequestBody UsuarioModel usuarioDetails) {
        return usuarioService.atualizarUsuario(id, usuarioDetails);
    }

    // Deletar um usuário
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUsuario(@PathVariable Long id) {
        usuarioService.deletarUsuario(id);
        return ResponseEntity.ok("Usuário com ID " + id + " foi deletado com sucesso.");
    }

    // Endpoint para filtrar usuários por ranking
    @GetMapping("/ranking/{ranking}")
    public ResponseEntity<List<UsuarioModel>> getUsuariosByRanking(@PathVariable String ranking) {
        List<UsuarioModel> usuariosFiltrados = usuarioService.listarUsuarios().stream()
                .filter(usuario -> usuario.determinarRanking().equals(ranking))
                .collect(Collectors.toList());

        if (usuariosFiltrados.isEmpty()) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.ok(usuariosFiltrados);
    }
}
