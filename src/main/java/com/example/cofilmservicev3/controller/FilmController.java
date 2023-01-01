package com.example.cofilmservicev3.controller;

import com.example.cofilmservicev3.model.Film;
import com.example.cofilmservicev3.repository.projection.FilmProjection;
import com.example.cofilmservicev3.service.FilmService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class FilmController {

    private final FilmService filmService;

    private final ModelMapper modelMapper;

    @GetMapping("/films")
    public ResponseEntity<List<FilmProjection>> listAllFilms(@PageableDefault Pageable pageRequest) {

        List<FilmProjection> films = filmService.getAllFilms(pageRequest);

        return ResponseEntity.status(HttpStatus.OK).body(films);
    }

    @GetMapping("/film/{id}")
    public ResponseEntity<Film> listFilm(@PathVariable Long id) {
        //TODO
        return ResponseEntity.ok().build();
    }
}
