package com.cm.abcapi.controller;

import com.cm.abcapi.model.Noticia;
import com.cm.abcapi.service.ConsultaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/consulta")
public class ConsultaController {

    private final ConsultaService consultaService;

    @Autowired
    public ConsultaController(ConsultaService consultaService) {
        this.consultaService = consultaService;
    }

    @GetMapping
    public List<Noticia> buscarNoticias(@RequestParam("q") String q) {
        return consultaService.buscarNoticias(q);
    }
}
