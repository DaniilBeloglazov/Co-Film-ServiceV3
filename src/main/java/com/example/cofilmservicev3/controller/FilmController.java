package com.example.cofilmservicev3.controller;

import com.example.cofilmservicev3.annotation.PageableEndpoint;
import com.example.cofilmservicev3.dto.CreateFilmRequest;
import com.example.cofilmservicev3.dto.UpdateFilmRequest;
import com.example.cofilmservicev3.model.Film;
import com.example.cofilmservicev3.model.Genre;
import com.example.cofilmservicev3.model.Person;
import com.example.cofilmservicev3.repository.projection.FilmProjection;
import com.example.cofilmservicev3.service.FilmService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

@Tag(name = "Film Controller", description = "Used for interaction with Film entity. Also used for adding Persons related to films")
@RestController
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
        modelMapper.addConverter(new AbstractConverter<Long, Genre>() {
            @Override
            protected Genre convert(Long source) { // For mapping List<Long> to List<Person>
                Genre genre = new Genre();
                genre.setId(source);
                return genre;
            }
        });
    }
    @Operation(summary = "Used to list all existing Films")
    @GetMapping("/films")
    @PageableEndpoint
    public ResponseEntity<List<FilmProjection>> listAllFilms(
            @PageableDefault(size = 10, sort = "desc,productionYear") @Parameter(hidden = true) Pageable pageable) {

        List<FilmProjection> films = filmService.getAllFilms(pageable);

        return ResponseEntity.status(HttpStatus.OK).body(films);
    }
    @Operation(summary = "Used to list specific Film by id.")
    @GetMapping("/films/{id}")
    public ResponseEntity<FilmProjection> listFilm(@PathVariable Long id) {

        FilmProjection film = filmService.getFilm(id);

        return ResponseEntity.status(HttpStatus.OK).body(film);
    }
    @Operation(summary = "Used to create Film.")
    @PostMapping(value = "/film", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> createFilm(@Validated CreateFilmRequest createFilmRequest) throws IOException {

        Film filmToCreate = modelMapper.map(createFilmRequest, Film.class);
        filmService.createFilm(filmToCreate, createFilmRequest.getPoster());

        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @Operation(summary = "Used to update Film by id. All parameters are optional")
    @PatchMapping(value = "/films/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateFilm(
            @PathVariable Long id,
            @Validated UpdateFilmRequest updateRequest
            ) throws IOException, InvocationTargetException, IllegalAccessException {

        Film updatedFilm = modelMapper.map(updateRequest, Film.class);
        filmService.updateFilm(id, updatedFilm, updateRequest.getPoster());

        return ResponseEntity.ok().build();
    }
    @Operation(summary = "Used to delete Film by id.")
    @DeleteMapping("/films/{id}")
    public ResponseEntity<Void> deleteFilm(@PathVariable Long id) {

        filmService.deleteFilm(id);

        return ResponseEntity.ok().build();
    }
}
