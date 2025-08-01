package com.alura.literatura.model;

import com.alura.literatura.entidad.Autor;
import com.alura.literatura.entidad.Libro;

import java.util.List;
import java.util.stream.Collectors;

public class ConsolaUtils{


    public static void imprimirLibro(Libro libro) {
        System.out.println("----- LIBRO -----");
        System.out.println("ID: " + libro.getId());
        System.out.println("Título: " + libro.getTitulo());

        List<String> idiomas = libro.getIdiomas();
        String idiomasStr = (idiomas == null || idiomas.isEmpty()) ? "N/A" : String.join(", ", idiomas);
        System.out.println("Idiomas: " + idiomasStr);

        System.out.println("Descargas: " + libro.getDescargas());

        List<Autor> autores = libro.getAutores();
        String autoresStr = (autores == null || autores.isEmpty())
                ? "N/A"
                : autores.stream().map(Autor::getNombre).collect(Collectors.joining(", "));
        System.out.println("Autor(es): " + autoresStr);
        System.out.println("-----------------");
    }

    public static void imprimirAutor(Autor autor) {
        System.out.println("----- AUTOR -----");
        System.out.println("ID: " + autor.getId());
        System.out.println("Nombre: " + autor.getNombre());
        System.out.println("Nacimiento: " + autor.getNacimiento());
        System.out.println("Fallecimiento: " + autor.getFallecimiento());
        System.out.println("-----------------");
    }


    public static void imprimirAutorConLibros(Autor autor) {
        System.out.println("Autor: " + autor.getNombre());
        System.out.println("Fecha de nacimiento: " + autor.getNacimiento());
        System.out.println("Fecha de fallecimiento: " + autor.getFallecimiento());
        String libros = autor.getLibros().stream()
                .map(Libro::getTitulo)
                .collect(Collectors.joining(", ", "[", "]"));
        System.out.println("Libros: " + libros);
        System.out.println();
    }
}







