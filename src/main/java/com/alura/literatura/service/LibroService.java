package com.alura.literatura.service;
import com.alura.literatura.entidad.Libro;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LibroService {

    private final ILibroRepo libroRepo;

    public LibroService(ILibroRepo libroRepo) {
        this.libroRepo = libroRepo;
    }

    @Transactional(readOnly = true)
    public List<Libro> obtenerTodos() {
        return libroRepo.findAll();
    }


}
