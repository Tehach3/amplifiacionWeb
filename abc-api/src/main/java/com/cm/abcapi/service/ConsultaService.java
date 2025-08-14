package com.cm.abcapi.service;

import com.cm.abcapi.Exceptions.BadRequestException;
import com.cm.abcapi.Exceptions.NotFoundException;
import com.cm.abcapi.model.Noticia;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class ConsultaService {

    private static final Logger log = LoggerFactory.getLogger(ConsultaService.class);
    private static final String BASE = "https://www.abc.com.py/buscar?query=";

    public List<Noticia> buscarNoticias(String q) {
        if (q == null || q.isBlank()) {
            throw new BadRequestException("Parámetros inválidos");
        }

        List<Noticia> noticias = new ArrayList<>();

        try {
            String url = BASE + URLEncoder.encode(q, StandardCharsets.UTF_8);
            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0")
                    .referrer("https://www.google.com/")
                    .timeout(10000)
                    .get();

            Elements items = doc.select("article");

            for (Element item : items) {
                String titulo = text(item.selectFirst("h2"));
                String resumen = text(item.selectFirst("p"));
                String enlace = item.selectFirst("a[href]") != null ? item.selectFirst("a[href]").absUrl("href") : "";
                String fecha = text(item.selectFirst("time"));
                String foto = "";

                Element img = item.selectFirst("img");
                if (img != null) {
                    foto = img.hasAttr("data-src") ? img.absUrl("data-src") : img.absUrl("src");
                }

                if (!titulo.isEmpty() && !enlace.isEmpty()) {
                    noticias.add(new Noticia(fecha, enlace, foto, titulo, resumen));
                }
            }

            if (noticias.isEmpty()) {
                throw new NotFoundException("No se encuentran noticias para el texto: " + q);
            }

            return noticias;

        } catch (BadRequestException | NotFoundException e) {
            // Estas sí se propagan correctamente
            throw e;
        } catch (Exception e) {
            log.error("Error interno al buscar noticias", e);
            // No envíes RuntimeException, porque no la estás manejando bien en el GlobalExceptionHandler
            throw new RuntimeException("Error interno del servidor", e);
        }
    }

    private String text(Element e) {
        return e == null ? "" : e.text().trim();
    }
}
