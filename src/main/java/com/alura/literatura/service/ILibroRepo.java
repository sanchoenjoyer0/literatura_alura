package com.alura.literatura.service;

import com.alura.literatura.entidad.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ILibroRepo extends JpaRepository <Libro,Long> {
    List<Libro> findByIdiomasContainingIgnoreCase(String idioma);
    List<Libro> findByTituloIgnoreCase(String titulo);
}
