package com.example.cofilmservicev3.repository;

import com.example.cofilmservicev3.model.Person;
import com.example.cofilmservicev3.repository.projection.PersonProjection;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    @Query("SELECT person " +
            "FROM Person person " +
                "LEFT JOIN person.photos photos " +
                "LEFT JOIN person.directedFilms directedFilms " +
                "LEFT JOIN person.writtenFilms writtenFIlms " +
                "LEFT JOIN person.actoredFilms actoredFilms")
    List<PersonProjection> findAllProjections(Pageable pageable);
    @Query("SELECT DISTINCT person " + // DISTINCT Optional
            "FROM Person person " +
                "LEFT JOIN person.photos photos " +
                "LEFT JOIN person.directedFilms directedFilms " +
                "LEFT JOIN person.writtenFilms writers " +
                "LEFT JOIN person.actoredFilms actors " +
            "WHERE person.id = :id")
    Optional<PersonProjection> findProjection(@Param("id") Long id);
}
