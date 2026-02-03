// src/main/java/com/delas/api/service/GeocodeService.java
package com.delas.api.service;

import com.delas.api.dto.GeocodeResponse;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

@Service
public class GeocodeService {

  private final RestTemplate restTemplate = new RestTemplate();

  public GeocodeResponse geocode(String query) {
    String url = UriComponentsBuilder
      .fromHttpUrl("https://nominatim.openstreetmap.org/search")
      .queryParam("q", query)
      .queryParam("format", "json")
      .queryParam("limit", 1)
      .build(true)
      .toUriString();

    HttpHeaders headers = new HttpHeaders();
    headers.set(HttpHeaders.USER_AGENT, "MaisDelas/1.0 (contact: seuemail@dominio.com)");
    headers.setAccept(List.of(MediaType.APPLICATION_JSON));

    HttpEntity<Void> entity = new HttpEntity<>(headers);

    ResponseEntity<List> response = restTemplate.exchange(
      url,
      HttpMethod.GET,
      entity,
      List.class
    );

    if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null || response.getBody().isEmpty()) {
      return null;
    }

    // Nominatim retorna array de objetos; pegamos o primeiro com lat/lon
    Map first = (Map) response.getBody().get(0);
    Object latObj = first.get("lat");
    Object lonObj = first.get("lon");
    if (latObj == null || lonObj == null) return null;

    Double lat = Double.valueOf(latObj.toString());
    Double lon = Double.valueOf(lonObj.toString());
    return new GeocodeResponse(lat, lon);
  }
}
