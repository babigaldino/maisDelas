package com.delas.api.controller;
import com.delas.api.model.UsuarioModel;
import com.delas.api.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioModel> createUsuario(@RequestBody UsuarioModel usuario) {
        return ResponseEntity.status(201).body(usuarioService.salvarUsuario(usuario));
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

    @PutMapping("/{id}")
    public UsuarioModel updateUsuario(@PathVariable Long id, @RequestBody UsuarioModel usuarioDetails) {
        return usuarioService.atualizarUsuario(id, usuarioDetails);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        return usuarioService.deletarUsuario(id) ? ResponseEntity.status(204).build() : ResponseEntity.status(404).build();


    }
    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .body("{\"message\":\"API está funcionando corretamente!\"}");
    }
}
