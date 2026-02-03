// src/main/java/com/delas/api/controller/GeocodeController.java
package com.delas.api.controller;

import com.delas.api.dto.GeocodeResponse;
import com.delas.api.service.GeocodeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/geocode")
public class GeocodeController {

  private final GeocodeService geocodeService;

  public GeocodeController(GeocodeService geocodeService) {
    this.geocodeService = geocodeService;
  }

  @GetMapping
  public ResponseEntity<?> geocode(@RequestParam String q) {
    GeocodeResponse result = geocodeService.geocode(q);
    if (result == null) return ResponseEntity.notFound().build();
    return ResponseEntity.ok(result);
  }
}
