package com.example.cofilmservicev3.controller;

import com.example.cofilmservicev3.annotation.PageableEndpoint;
import com.example.cofilmservicev3.dto.CreateFilmRequest;
import com.example.cofilmservicev3.dto.CreateFilmResponse;
import com.example.cofilmservicev3.dto.MessageResponse;
import com.example.cofilmservicev3.dto.UpdateFilmRequest;
import com.example.cofilmservicev3.model.Film;
import com.example.cofilmservicev3.model.Genre;
import com.example.cofilmservicev3.model.Person;
import com.example.cofilmservicev3.service.FilmService;
import com.example.cofilmservicev3.service.GenreService;
import com.example.cofilmservicev3.service.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.AbstractCondition;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

@Tag(name = "Film Controller", description = "Used for interaction with Film entity. Also used for adding Persons related to films")
@RestController
@Slf4j
@RequestMapping("/api/v1")
public class FilmController {

    private final FilmService filmService;
    private final PersonService personService;
    private final ModelMapper modelMapper;
    private final GenreService genreService;

    @Autowired
    public FilmController(FilmService filmService, PersonService personService, ModelMapper modelMapper, GenreService genreService) {
        this.filmService = filmService;
        this.personService = personService;
        this.modelMapper = modelMapper;
        this.genreService = genreService;
        modelMapper.createTypeMap(CreateFilmRequest.class, Film.class)
                .addMappings(mapper -> {
                    mapper.skip(Film::setId);
                    mapper.skip(Film::setPosterUri);
                    mapper.skip(Film::setGenres);
                    mapper.skip(Film::setDirectors);
                    mapper.skip(Film::setWriters);
                    mapper.skip(Film::setActors);
                });
        modelMapper.addConverter(new AbstractConverter<Long, Person>() {
            @Override
            protected Person convert(Long source) { // For mapping List<Long> to List<Person>
                return personService.getPerson(source);
            }
        });
        modelMapper.addConverter(new AbstractConverter<Long, Genre>() {
            @Override
            protected Genre convert(Long source) { // For mapping List<Long> to List<Person>
                return genreService.getGenre(source);
            }
        });
    }

    @Operation(summary = "Used to list all existing Films")
    @GetMapping("/films")
    @PageableEndpoint
    public ResponseEntity<List<Film>> listAllFilms(
            @PageableDefault(size = 10, sort = "productionYear", direction = Sort.Direction.DESC) @Parameter(hidden = true) Pageable
                    pageable,
            @Parameter(description = "Parameter for searching. (title)") @RequestParam(required = false) String
                    query) {

        List<Film> films = filmService.getAllFilms(pageable, query);

        return ResponseEntity.status(HttpStatus.OK).body(films);
    }

    @ApiResponse(
            description = "Film with requested id not found",
            responseCode = "404",
            content = @Content(schema = @Schema(implementation = MessageResponse.class)))
    @ApiResponse(description = "Film was listed", responseCode = "200")
    @Operation(summary = "Used to list specific Film by id.")
    @GetMapping("/films/{id}")
    public ResponseEntity<Film> listFilm(@Parameter(description = "Film id", example = "16") @PathVariable Long id) {

        Film film = filmService.getFilm(id);

        return ResponseEntity.status(HttpStatus.OK).body(film);
    }

    @ApiResponse(description = "Film was created", responseCode = "201")
    @ApiResponse(
            description = "Film with requested title already exists",
            responseCode = "400",
            content = @Content(schema = @Schema(implementation = MessageResponse.class)))
    @Operation(summary = "Used to create Film.")
    @PostMapping(value = "/film", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CreateFilmResponse> createFilm(@Validated CreateFilmRequest createFilmRequest) throws
            IOException {

        Film filmToCreate = modelMapper.map(createFilmRequest, Film.class);

        Long createdFilmId = filmService.createFilm(filmToCreate, createFilmRequest.getPoster());

        CreateFilmResponse responseBody = new CreateFilmResponse(createdFilmId);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
    }

    private List<Person> getPersons(Optional<List<Long>> personIds) {
        return personIds.map(ids -> ids.stream().map(personService::getPerson).toList()).orElse(null);
    }

    @ApiResponse(description = "Film was updated", responseCode = "200")
    @ApiResponse(
            description = "Film with requested title already exists",
            responseCode = "400",
            content = @Content(schema = @Schema(implementation = MessageResponse.class)))
    @ApiResponse(
            description = "Film with requested id not found",
            responseCode = "404",
            content = @Content(schema = @Schema(implementation = MessageResponse.class)))

    @Operation(summary = "Used to update Film by id. All parameters are optional")
    @PatchMapping(value = "/films/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateFilm(
            @Parameter(description = "Film id", example = "16") @PathVariable Long id,
            @Validated UpdateFilmRequest updateRequest
    ) throws IOException, InvocationTargetException, IllegalAccessException {
        Film updateMetadata = modelMapper.map(updateRequest, Film.class);
        filmService.updateFilm(id, updateMetadata, updateRequest.getPoster());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @ApiResponse(
            description = "Film was deleted",
            responseCode = "200"
    )
    @ApiResponse(
            description = "Film with requested id not found",
            responseCode = "404",
            content = @Content(schema = @Schema(implementation = MessageResponse.class, example = "Film with id: 16 not found")))
    @Operation(summary = "Used to delete Film by id.")
    @DeleteMapping("/films/{id}")
    public ResponseEntity<Void> deleteFilm(@Parameter(description = "Film id", example = "16") @PathVariable Long
                                                   id) throws IOException {

        filmService.deleteFilm(id);

        return ResponseEntity.ok().build();
    }
}
