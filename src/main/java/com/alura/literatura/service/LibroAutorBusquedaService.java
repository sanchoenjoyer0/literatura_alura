package com.alura.literatura.service;

import com.alura.literatura.entidad.Autor;
import com.alura.literatura.entidad.Libro;
import com.alura.literatura.model.DatosAutor;
import com.alura.literatura.model.DatosLibro;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LibroAutorBusquedaService {

    private final ObjectMapper mapper;
    private final IAutorRepo autorRepo;
    private final ILibroRepo libroRepo;

    // Constructor con inyección de dependencias
    public LibroAutorBusquedaService(IAutorRepo autorRepo, ILibroRepo libroRepo) {
        this.mapper = new ObjectMapper();
        this.mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        this.autorRepo = autorRepo;
        this.libroRepo = libroRepo;
    }

    public Optional<Libro> buscarLibroPorTitulo(String titulo) {
        String url = "https://gutendex.com/books/?search=" + titulo.replace(" ", "+");

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Accept", "application/json")
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();

            if (responseBody == null || responseBody.isBlank()) {
                return Optional.empty();
            }

            GuntendexResponse respuesta = mapper.readValue(responseBody, GuntendexResponse.class);
            if (respuesta.results() == null || respuesta.results().isEmpty()) {
                return Optional.empty();
            }

            DatosLibro datos = respuesta.results().get(0);

            Libro libro = new Libro();
            libro.setTitulo(datos.titulo());
            libro.setIdiomas(datos.idiomas());
            libro.setDescargas(datos.totalDescargas());

            List<Autor> autoresDelLibro = new ArrayList<>();

            if (datos.autores() != null) {
                for (DatosAutor datosAutor : datos.autores()) {
                    Autor autor = new Autor();
                    autor.setNombre(datosAutor.nombre());
                    autor.setNacimiento(datosAutor.nacimiento());
                    autor.setFallecimiento(datosAutor.fallecimiento());
                    autoresDelLibro.add(autor); // aún no persistido
                }
            }

            libro.setAutores(autoresDelLibro);
            return Optional.of(libro); // Solo retorna, no guarda

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        return Optional.empty();
    }
}