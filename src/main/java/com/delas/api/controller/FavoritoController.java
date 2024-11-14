package com.delas.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("favorito")
public class FavoritoController {

    @Autowired
    private FavoritoController favoritoController;
}