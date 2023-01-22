package com.example.cofilmservicev3.repository;

import com.example.cofilmservicev3.model.Film;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FilmRepository extends JpaRepository<Film, Long> {

    boolean existsByTitle(String title);

    @Query("SELECT film FROM Film film " +
            "WHERE LOWER(film.title) LIKE %:search%")
    List<Film> findAllSearch(@Param("search") String searchText, Pageable pageable);
    @Query("SELECT film FROM Film film ")
    List<Film> shortFindAll(Pageable pageable);
    @Query("SELECT film FROM Film film WHERE film.id = :id")
    Optional<Film> shortFindById(@Param("id") Long id);

}
