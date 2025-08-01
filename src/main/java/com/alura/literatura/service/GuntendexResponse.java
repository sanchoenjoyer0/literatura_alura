package com.alura.literatura.service;

import com.alura.literatura.model.DatosLibro;

import java.util.List;

public record GuntendexResponse(

        int count,
        String next,
        String previous,
        List<DatosLibro> results) {

}
