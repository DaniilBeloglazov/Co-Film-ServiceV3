package com.example.cofilmservicev3.repository;

import com.example.cofilmservicev3.model.Film;
import com.example.cofilmservicev3.repository.projection.FilmProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.hibernate.loader.Loader.SELECT;

@Repository
public interface FilmRepository extends JpaRepository<Film, Long> {

    @Query("SELECT f.id AS id, " +
                "f.title AS title, " +
                "f.posterPath as posterPath, " +
                "dir AS directors, " +
                "wr AS writers, " +
                "act AS actors " +
            "FROM Film f " +
            "LEFT JOIN f.directors AS dir " +
            "LEFT JOIN f.writers AS wr " +
            "LEFT JOIN f.actors AS act")
    List<FilmProjection> shortFindAll();
    @Query("SELECT f.id AS id, " +
                "f.title AS title, " +
                "f.posterPath as posterPath, " +
                "dir AS directors, " +
                "wr AS writers, " +
                "act AS actors " +
            "FROM Film f " +
            "LEFT JOIN f.directors AS dir " +
            "LEFT JOIN f.writers AS wr " +
            "LEFT JOIN f.actors AS act " +
            "WHERE f.id = :id")
    FilmProjection shortFindById(Long id);

}
