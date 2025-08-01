package com.alura.literatura.service;

import com.alura.literatura.entidad.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IAutorRepo extends JpaRepository<Autor, Long> {


    List<Autor> findByNacimientoLessThanEqualAndFallecimientoGreaterThanEqual(Integer año, Integer año2);

    Optional<Autor> findByNombreIgnoreCase(String nombre);

}
