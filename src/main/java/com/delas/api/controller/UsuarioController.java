package com.delas.api.controller;

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


    // Endpoint para editar o perfil do usuário
    @PutMapping("/{id}")
    public UsuarioModel updateUsuario(@PathVariable Long id, @RequestBody UsuarioModel usuarioDetails) {
        return usuarioService.atualizarUsuario(id, usuarioDetails);
    }

    @GetMapping
    public List<UsuarioModel> getAllUsuarios() {
        return usuarioService.listarUsuarios();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioModel> getUsuarioById(@PathVariable Long id) {
        return usuarioService.buscarUsuarioPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(404).build());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUsuario(@PathVariable Long id) {
        usuarioService.deletarUsuario(id);
        return ResponseEntity.ok("Usuário com ID " + id + " foi deletado com sucesso.");
    }

}
