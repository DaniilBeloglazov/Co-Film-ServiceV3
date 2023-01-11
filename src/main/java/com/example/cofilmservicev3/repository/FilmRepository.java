package com.example.cofilmservicev3.repository;

import com.example.cofilmservicev3.model.Film;
import com.example.cofilmservicev3.repository.projection.FilmProjection;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilmRepository extends JpaRepository<Film, Long> {

    @Query("SELECT film " +
            "FROM Film film " +
                "LEFT JOIN film.genres as genres " +
                "LEFT JOIN film.directors AS directors " +
                "LEFT JOIN film.writers AS writers " +
                "LEFT JOIN film.actors AS actors ")
    List<FilmProjection> shortFindAll(Pageable pageable);
    @Query("SELECT DISTINCT film " +
            "FROM Film film " +
                "LEFT JOIN film.genres as genres " +
                "LEFT JOIN film.directors AS directors " +
                "LEFT JOIN film.writers AS writers " +
                "LEFT JOIN film.actors AS actors " +
            "WHERE film.id = :id")
    FilmProjection shortFindById(Long id);

}
