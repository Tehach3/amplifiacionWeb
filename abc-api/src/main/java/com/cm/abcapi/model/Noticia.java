package com.cm.abcapi.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
//@AllArgsConstructor

public class Noticia {

    private String fecha;
    private String enlace;
    private String enlace_foto;
    private String titulo;
    private String resumen;

    public Noticia(String fecha, String enlace, String enlaceFoto, String titulo, String resumen) {
    }
}
