package com.example.cofilmservicev3.service;

import com.example.cofilmservicev3.model.Genre;
import com.example.cofilmservicev3.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.text.MessageFormat;

@Service
@RequiredArgsConstructor
public class GenreService {

    private final GenreRepository genreRepository;

    public Genre getGenre(Long id) {
        return genreRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        MessageFormat.format("Genre with id: {0} not found", id)
                ));
    }
}
