package com.example.cofilmservicev3.service;

import com.example.cofilmservicev3.model.Film;
import com.example.cofilmservicev3.model.Person;
import com.example.cofilmservicev3.repository.FilmRepository;
import com.example.cofilmservicev3.repository.PersonRepository;
import com.example.cofilmservicev3.repository.projection.FilmProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FilmService {

    private final FilmRepository filmRepository;
    private final PersonRepository personRepository;

    public List<FilmProjection> getAllFilms(Pageable pageable) {

        List<FilmProjection> filmProjections = filmRepository.shortFindAll();

        return filmProjections;
    }

    public FilmProjection getFilmById(Long id) {

        FilmProjection foundedFilm = filmRepository.shortFindById(id);

        return foundedFilm;
    }

    public void createFilm(Film filmToCreate, MultipartFile posterImage) {

        filmToCreate.setDirectors(filmToCreate.getDirectors().stream()
                .map(person -> personRepository.findById(person.getId())
                        .orElseThrow(() -> new RuntimeException(""))).collect(Collectors.toList()));
        filmToCreate.setWriters(filmToCreate.getWriters().stream()
                .map(person -> personRepository.findById(person.getId())
                        .orElseThrow(() -> new RuntimeException(""))).collect(Collectors.toList()));
        filmToCreate.setActors(filmToCreate.getActors().stream()
                .map(person -> personRepository.findById(person.getId())
                        .orElseThrow(() -> new RuntimeException(""))).collect(Collectors.toList()));

        filmRepository.save(filmToCreate);
    }
}
