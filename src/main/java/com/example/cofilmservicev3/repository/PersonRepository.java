package com.example.cofilmservicev3.repository;

import com.example.cofilmservicev3.model.Person;
import com.example.cofilmservicev3.repository.projection.PersonProjection;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    @Query("SELECT p.id as id, " +
                "p.name as name, " +
                "p.lastName as lastName, " +
                "p.posterPath as posterPath, " +
                "ph as photos, " +
                "dir as directedFilms, " +
                "wr as writtenFilms, " +
                "act as actoredFilms " +
            "FROM Person p " +
            "LEFT JOIN p.photos ph " +
            "LEFT JOIN p.directedFilms dir " +
            "LEFT JOIN p.writtenFilms wr " +
            "LEFT JOIN p.actoredFilms act")
    List<PersonProjection> findAllProjections(Pageable pageable);
    @Query("SELECT p.id as id, " +
                "p.name as name, " +
                "p.lastName as lastName, " +
                "p.posterPath as posterPath, " +
                "ph as photos, " +
                "dir as directedFilms, " +
                "wr as writtenFilms, " +
                "act as actoredFilms " +
            "FROM Person p " +
            "LEFT JOIN p.photos ph " +
            "LEFT JOIN p.directedFilms dir " +
            "LEFT JOIN p.writtenFilms wr " +
            "LEFT JOIN p.actoredFilms act " +
            "WHERE p.id = :id")
    PersonProjection findProjection(Long id);
}
