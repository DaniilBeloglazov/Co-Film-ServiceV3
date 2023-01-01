package com.example.cofilmservicev3.service;

import com.example.cofilmservicev3.model.Film;
import com.example.cofilmservicev3.repository.FilmRepository;
import com.example.cofilmservicev3.repository.projection.FilmProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FilmService {

    private final FilmRepository filmRepository;

    public List<FilmProjection> getAllFilms(Pageable pageable) {

        List<FilmProjection> filmProjections = filmRepository.shortFindAll();

        return filmProjections;
    }
}
