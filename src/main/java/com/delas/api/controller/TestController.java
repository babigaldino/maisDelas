package com.delas.api.controller; // Certifique-se de usar o pacote correto para o seu projeto

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/test")
    public String test() {
        return "API está funcionando!";
    }
}

