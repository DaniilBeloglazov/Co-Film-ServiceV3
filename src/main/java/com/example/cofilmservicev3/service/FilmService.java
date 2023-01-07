package com.example.cofilmservicev3.service;

import com.example.cofilmservicev3.exception.FilmNotFoundException;
import com.example.cofilmservicev3.exception.PersonNotFoundException;
import com.example.cofilmservicev3.model.Film;
import com.example.cofilmservicev3.repository.FilmRepository;
import com.example.cofilmservicev3.repository.PersonRepository;
import com.example.cofilmservicev3.repository.projection.FilmProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FilmService {

    private final String imageRoot = "Need to Configure"; //TODO
    private final FilmRepository filmRepository;
    private final PersonRepository personRepository;

    public List<FilmProjection> getAllFilms(Pageable pageable) {

        List<FilmProjection> filmProjections = filmRepository.shortFindAll();

        return filmProjections;
    }

    public FilmProjection getFilmById(Long id) {

        return filmRepository.shortFindById(id);
    }
    @Transactional
    public void createFilm(Film filmToCreate, MultipartFile posterImage) throws IOException {

        File posterImageFile = new File(imageRoot + UUID.randomUUID() + "." + posterImage.getContentType().split("/")[1]);

        filmToCreate.setPosterPath(posterImageFile.getPath());

        filmToCreate.setDirectors(filmToCreate.getDirectors().stream()
                .map(person -> personRepository.findById(person.getId())
                        .orElseThrow(() -> new PersonNotFoundException(
                                MessageFormat.format("Person with id: {0} not found", person.getId())
                        )))
                .collect(Collectors.toList()));

        filmToCreate.setWriters(filmToCreate.getWriters().stream()
                .map(person -> personRepository.findById(person.getId())
                        .orElseThrow(() -> new PersonNotFoundException(
                                MessageFormat.format("Person with id: {0} not found", person.getId())
                        )))
                .collect(Collectors.toList()));

        filmToCreate.setActors(filmToCreate.getActors().stream()
                .map(person -> personRepository.findById(person.getId())
                        .orElseThrow(() -> new PersonNotFoundException(
                                MessageFormat.format("Person with id: {0} not found", person.getId())
                        )))
                .collect(Collectors.toList()));

        filmRepository.save(filmToCreate);

        posterImage.transferTo(posterImageFile);
    }

    public void updateFilmMetadata(Long id, Film updatedFilm) {

        if (!filmRepository.existsById(id))
            throw new FilmNotFoundException(MessageFormat.format("Film with id: {0} not found", id));

        updatedFilm.setId(id);
        filmRepository.save(updatedFilm);
    }

    public void updateFilmPosterImage(Long id, MultipartFile updatedImage) throws IOException {

        Film filmToUpdate = filmRepository.findById(id)
                .orElseThrow(() -> new FilmNotFoundException(""));

        Files.delete(Path.of(filmToUpdate.getPosterPath()));

        File posterImageFile = new File(imageRoot + UUID.randomUUID() + "." + updatedImage.getContentType().split("/")[1]);

        filmToUpdate.setPosterPath(posterImageFile.getAbsolutePath());

        filmRepository.save(filmToUpdate);

        updatedImage.transferTo(posterImageFile);
    }

    public void deleteFilm(Long id) {

        if (!filmRepository.existsById(id))
            throw new FilmNotFoundException(MessageFormat.format("Film with id: {0} not found", id));

        filmRepository.deleteById(id);
    }
}
