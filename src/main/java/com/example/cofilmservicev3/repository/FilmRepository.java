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

    @Query("SELECT film.id as id, " +
                "film.title as title, " +
                "film.description as description, " +
                "film.productionYear as productionYear, " +
                "film.ageRating as ageRating, " +
                "film.audience as audience, " +
                "film.boxOffice as boxOffice, " +
                "film.budget as budget, " +
                "dir AS directors, " +
                "wr AS writers, " +
                "act AS actors, " +
                "gen AS genres " +
            "FROM Film film " +
            "LEFT JOIN film.genres as gen " +
            "LEFT JOIN film.directors AS dir " +
            "LEFT JOIN film.writers AS wr " +
            "LEFT JOIN film.actors AS act ")
    List<FilmProjection> shortFindAll(Pageable pageable);
    @Query("SELECT film.id as id, " +
                "film.title as title, " +
                "film.description as description, " +
                "film.productionYear as productionYear, " +
                "film.ageRating as ageRating, " +
                "film.audience as audience, " +
                "film.boxOffice as boxOffice, " +
                "film.budget as budget, " +
                "dir AS directors, " +
                "wr AS writers, " +
                "act AS actors, " +
                "gen AS genres " +
            "FROM Film film " +
            "LEFT JOIN film.genres as gen " +
            "LEFT JOIN film.directors AS dir " +
            "LEFT JOIN film.writers AS wr " +
            "LEFT JOIN film.actors AS act " +
            "WHERE film.id = :id")
    FilmProjection shortFindById(Long id);

}
