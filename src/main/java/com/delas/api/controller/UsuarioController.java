package com.delas.api.controller;

import com.delas.api.dto.UsuarioRequestDTO;
import com.delas.api.dto.UsuarioResponseDTO;
import com.delas.api.model.ServicosModel;
import com.delas.api.model.UsuarioModel;
import com.delas.api.repository.ServicosRepository;
import com.delas.api.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ServicosRepository servicosRepository;

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> getAllUsuarios() {
        List<UsuarioResponseDTO> usuarios = usuarioService.listarUsuarios()
                .stream()
                .map(UsuarioResponseDTO::fromModel)
                .collect(Collectors.toList());
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> getUsuarioById(@PathVariable Long id) {
        return usuarioService.buscarUsuarioPorId(id)
                .map(usuario -> {
                    UsuarioResponseDTO dto = UsuarioResponseDTO.fromModel(usuario);
                    
                    // ✅ ADICIONA SERVIÇOS se for PRESTADOR
                    if (usuario.getTipo() == UsuarioModel.TipoUsuario.PRESTADOR) {
                        List<ServicosModel> servicos = servicosRepository.findByUsuarioId(usuario.getId());
                        dto.setServicos(servicos);
                    }
                    
                    return ResponseEntity.ok(dto);
                })
                .orElseGet(() -> ResponseEntity.status(404).build());
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<UsuarioResponseDTO>> getUsuariosPorTipo(@PathVariable String tipo) {
        try {
            String tipoUpperCase = tipo.toUpperCase();
            
            List<UsuarioResponseDTO> usuarios = usuarioService.listarUsuarios()
                    .stream()
                    .filter(usuario -> usuario.getTipo().toString().equals(tipoUpperCase))
                    .map(usuario -> {
                        UsuarioResponseDTO dto = UsuarioResponseDTO.fromModel(usuario);
                        
                        // ✅ ADICIONA SERVIÇOS se for PRESTADOR
                        if (usuario.getTipo() == UsuarioModel.TipoUsuario.PRESTADOR) {
                            List<ServicosModel> servicos = servicosRepository.findByUsuarioId(usuario.getId());
                            dto.setServicos(servicos);
                        }
                        
                        return dto;
                    })
                    .collect(Collectors.toList());
            
            return ResponseEntity.ok(usuarios);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> updateUsuario(
            @PathVariable Long id,
            @Valid @RequestBody UsuarioRequestDTO usuarioDetails) {
        try {
            UsuarioModel usuario = usuarioService.atualizarUsuario(id, usuarioDetails);
            return ResponseEntity.ok(UsuarioResponseDTO.fromModel(usuario));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUsuario(@PathVariable Long id) {
        usuarioService.deletarUsuario(id);
        return ResponseEntity.ok("Usuário com ID " + id + " foi deletado com sucesso.");
    }
}
