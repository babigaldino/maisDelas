package com.delas.api.controller;

import com.delas.api.model.ServicosModel;
import com.delas.api.model.UsuarioModel;
import com.delas.api.repository.ServicosRepository;
import com.delas.api.repository.UsuarioRepository;
import com.delas.api.service.UploadService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/upload")
public class UploadController {

  private final UploadService uploadService;
  private final UsuarioRepository usuarioRepository;
  private final ServicosRepository servicosRepository;

  public UploadController(
      UploadService uploadService,
      UsuarioRepository usuarioRepository,
      ServicosRepository servicosRepository
  ) {
    this.uploadService = uploadService;
    this.usuarioRepository = usuarioRepository;
    this.servicosRepository = servicosRepository;
  }

  @PostMapping(
      value = "/usuario/{id}/foto",
      consumes = MediaType.MULTIPART_FORM_DATA_VALUE
  )
  public ResponseEntity<?> uploadFotoPerfil(
      @PathVariable Long id,
      @RequestParam("file") MultipartFile file
  ) throws Exception {

    if (file == null || file.isEmpty()) {
      return ResponseEntity.badRequest().body("Arquivo inválido.");
    }

    UsuarioModel user = usuarioRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

    Map result = uploadService.uploadProfile(file, id);
    String url = result.get("secure_url").toString();

    user.setFoto(url);
    usuarioRepository.save(user);

    return ResponseEntity.ok(Map.of("url", url));
  }

  @PostMapping(
      value = "/servicos/{id}/fotos",
      consumes = MediaType.MULTIPART_FORM_DATA_VALUE
  )
  public ResponseEntity<?> uploadFotosServico(
      @PathVariable Long id,
      @RequestParam("files") List<MultipartFile> files,
      @RequestParam(name = "append", defaultValue = "false") boolean append
  ) throws Exception {

    if (files == null || files.isEmpty()) {
      return ResponseEntity.badRequest().body("Envie pelo menos 1 foto.");
    }

    // remove vazios (caso o front mande algum slot vazio)
    List<MultipartFile> validFiles = files.stream()
        .filter(f -> f != null && !f.isEmpty())
        .toList();

    if (validFiles.isEmpty()) {
      return ResponseEntity.badRequest().body("Envie pelo menos 1 foto válida.");
    }

    ServicosModel servico = servicosRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));

    List<String> atuais = servico.getFotos() == null ? new ArrayList<>() : new ArrayList<>(servico.getFotos());

    int totalFinal = append ? (atuais.size() + validFiles.size()) : validFiles.size();
    if (totalFinal > 3) {
      return ResponseEntity.badRequest().body("Máximo 3 fotos por serviço.");
    }

    List<String> novasUrls = new ArrayList<>();
    for (int i = 0; i < validFiles.size(); i++) {
      // índice pra não sobrescrever quando append=true
      int index = append ? (atuais.size() + i) : i;
      Map result = uploadService.uploadServicePhoto(validFiles.get(i), id, index);
      novasUrls.add(result.get("secure_url").toString());
    }

    if (append) {
      atuais.addAll(novasUrls);
      servico.setFotos(atuais);
    } else {
      servico.setFotos(novasUrls);
    }

    servicosRepository.save(servico);
    return ResponseEntity.ok(servico.getFotos());
  }
}
