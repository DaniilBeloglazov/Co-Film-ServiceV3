package com.example.cofilmservicev3.controller;

import com.example.cofilmservicev3.annotation.MultipartSize;
import com.example.cofilmservicev3.annotation.PageableEndpoint;
import com.example.cofilmservicev3.dto.CreatePersonRequest;
import com.example.cofilmservicev3.dto.CreatePersonResponse;
import com.example.cofilmservicev3.dto.UpdatePersonRequest;
import com.example.cofilmservicev3.model.Person;
import com.example.cofilmservicev3.repository.projection.PersonProjection;
import com.example.cofilmservicev3.service.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.modelmapper.ModelMapper;
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
@Tag(name = "Person Controller", description = "Used for interaction with Person entity.")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PersonController {
    private final PersonService personService;

    private final ModelMapper modelMapper;

    @Operation(summary = "Used to list all existing Persons.")
    @GetMapping("/persons")
    @PageableEndpoint
    public ResponseEntity<List<PersonProjection>> listPersons(
            @PageableDefault(size = 10, sort = "name", direction = Sort.Direction.ASC) @Parameter(hidden = true) Pageable pageable,
            @RequestParam(required = false) String query) {

        List<PersonProjection> persons = personService.getAllPersons(pageable, query);

        return ResponseEntity.status(HttpStatus.OK).body(persons);
    }

    @Operation(summary = "Used to list specific Person by id.")
    @GetMapping("/persons/{id}")
    public ResponseEntity<PersonProjection> listPerson(@PathVariable Long id) {

        PersonProjection person = personService.getPerson(id);

        return ResponseEntity.status(HttpStatus.OK).body(person);
    }

    @Operation(summary = "Used to create Person.")
    @PostMapping(value = "/person", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CreatePersonResponse> addPerson(@ModelAttribute @Validated CreatePersonRequest createPersonRequest) throws IOException {

        Person personToCreate = modelMapper.map(createPersonRequest, Person.class);
        Long createdPersonId = personService.createPerson(personToCreate, createPersonRequest.getPoster());
        val payload = new CreatePersonResponse(createdPersonId);

        return ResponseEntity.status(HttpStatus.CREATED).body(payload);
    }

    @Operation(summary = "Used to update Person by id. All parameters are optional.")
    @PatchMapping(value = "/persons/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updatePerson(@PathVariable Long id, @Validated UpdatePersonRequest updatePersonRequest)
            throws InvocationTargetException, IllegalAccessException, IOException {

        Person updatedPerson = modelMapper.map(updatePersonRequest, Person.class);
        personService.updatePersonInfo(id, updatedPerson, updatePersonRequest.getPoster());

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "Used to update photos of specific Person.")
    @PutMapping(value = "/persons/{id}/photos", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updatePersonPhotos(
            @PathVariable Long id, @Validated @RequestPart @MultipartSize(min = 128, max = 4 << 20) MultipartFile[] photos) throws IOException {

        personService.updatePersonPhotos(id, photos);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    @Operation(summary = "Used to delete specific Person by id.")
    @DeleteMapping("/persons/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable Long id) {

        return ResponseEntity.ok().build();
    }
}
