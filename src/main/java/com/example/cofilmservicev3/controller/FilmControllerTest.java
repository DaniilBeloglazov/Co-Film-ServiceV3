package com.example.cofilmservicev3.controller;

import com.example.cofilmservicev3.annotation.PageableEndpoint;
import com.example.cofilmservicev3.dto.PersonSearchParams;
import com.example.cofilmservicev3.repository.projection.PersonProjection;
import com.example.cofilmservicev3.service.PersonService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FilmControllerTest {
    private final PersonService personService;

    private final ModelMapper modelMapper;

    @GetMapping("/api/v2/person/{id}")
    @PageableEndpoint
    public ResponseEntity<PersonProjection> listPerson(
            @Parameter(hidden = true) Pageable pageable,
            PersonSearchParams personSearchParams)
    {
        PersonProjection  loadedPerson = personService.testingPersonSearch(pageable, personSearchParams);

        return ResponseEntity.status(HttpStatus.OK).body(loadedPerson);
    }
}
