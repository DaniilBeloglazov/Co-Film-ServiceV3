package com.example.cofilmservicev3.controller;

import com.example.cofilmservicev3.dto.CreateFilmRequest;
import com.example.cofilmservicev3.model.Film;
import com.example.cofilmservicev3.model.Person;
import com.example.cofilmservicev3.repository.projection.FilmProjection;
import com.example.cofilmservicev3.service.FilmService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/api/v1")
public class FilmController {

    private final FilmService filmService;

    private final ModelMapper modelMapper;

    @Autowired
    public FilmController(FilmService filmService, ModelMapper modelMapper) {
        this.filmService = filmService;
        this.modelMapper = modelMapper;
        modelMapper.createTypeMap(CreateFilmRequest.class, Film.class)
                .addMappings(mapper -> {
                    mapper.skip(Film::setId);
                    mapper.skip(Film::setPosterPath);
                });
        modelMapper.addConverter(new AbstractConverter<Long, Person>() {
            @Override
            protected Person convert(Long source) { // For mapping List<Long> to List<Person>
                Person person = new Person();
                person.setId(source);
                return person;
            }
        });
    }

    @GetMapping("/films")
    public ResponseEntity<List<FilmProjection>> listAllFilms(@PageableDefault Pageable pageRequest) {

        List<FilmProjection> films = filmService.getAllFilms(pageRequest);

        return ResponseEntity.status(HttpStatus.OK).body(films);
    }

    @GetMapping("/film/{id}")
    public ResponseEntity<FilmProjection> listFilm(@PathVariable Long id) {

        FilmProjection film = filmService.getFilmById(id);

        return ResponseEntity.status(HttpStatus.OK).body(film);
    }

    @PostMapping(value = "/film", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> createFilm(@Validated CreateFilmRequest createFilmRequest) throws IOException {

        Film filmToCreate = modelMapper.map(createFilmRequest, Film.class);

        filmService.createFilm(filmToCreate, createFilmRequest.getPosterImage());

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping("/film/{id}")
    public ResponseEntity<Void> updateFilmMetadata(@PathVariable Long id) {

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/film/{id}")
    public ResponseEntity<Void> updateFilmPoster(@PathVariable Long id, MultipartFile updatedPoster) {

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/film/{id}")
    public ResponseEntity<Void> deleteFilm(@PathVariable Long id) {

        return ResponseEntity.ok().build();
    }
}
