package com.example.cofilmservicev3.controller;

import com.example.cofilmservicev3.annotation.PageableEndpoint;
import com.example.cofilmservicev3.dto.CreateFilmRequest;
import com.example.cofilmservicev3.dto.CreateFilmResponse;
import com.example.cofilmservicev3.dto.MessageResponse;
import com.example.cofilmservicev3.dto.UpdateFilmRequest;
import com.example.cofilmservicev3.model.Film;
import com.example.cofilmservicev3.model.Genre;
import com.example.cofilmservicev3.model.Person;
import com.example.cofilmservicev3.repository.projection.FilmProjection;
import com.example.cofilmservicev3.service.FilmService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;

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
                    mapper.skip(Film::setAvatarUri);
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
            @PageableDefault(size = 10, sort = "productionYear", direction = Sort.Direction.DESC) @Parameter(hidden = true) Pageable pageable,
            @Parameter(description = "Parameter for searching. (title)") @RequestParam(required = false) String query) {

        List<FilmProjection> films = filmService.getAllFilms(pageable, query);

        return ResponseEntity.status(HttpStatus.OK).body(films);
    }

    @ApiResponse(
            description = "Film with requested id not found",
            responseCode = "404",
            content = @Content(schema = @Schema(implementation = MessageResponse.class)))
    @ApiResponse(description = "Film was listed", responseCode = "200")
    @Operation(summary = "Used to list specific Film by id.")
    @GetMapping("/films/{id}")
    public ResponseEntity<FilmProjection> listFilm(@Parameter(description = "Film id", example = "16") @PathVariable Long id) {

        FilmProjection film = filmService.getFilm(id);

        return ResponseEntity.status(HttpStatus.OK).body(film);
    }

    @ApiResponse(description = "Film was created", responseCode = "201")
    @ApiResponse(
            description = "Film with requested title already exists",
            responseCode = "400",
            content = @Content(schema = @Schema(implementation = MessageResponse.class)))
    @Operation(summary = "Used to create Film.")
    @PostMapping(value = "/film", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CreateFilmResponse> createFilm(@Validated CreateFilmRequest createFilmRequest) throws IOException {

        Film filmToCreate = modelMapper.map(createFilmRequest, Film.class);
        Long createdFilmId = filmService.createFilm(filmToCreate, createFilmRequest.getPoster());
        val payload = new CreateFilmResponse(createdFilmId);

        return ResponseEntity.status(HttpStatus.CREATED).body(payload);
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

        Film updatedFilm = modelMapper.map(updateRequest, Film.class);
        filmService.updateFilm(id, updatedFilm, updateRequest.getPoster());

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
    public ResponseEntity<Void> deleteFilm(@Parameter(description = "Film id", example = "16") @PathVariable Long id) {

        filmService.deleteFilm(id);

        return ResponseEntity.ok().build();
    }
}
