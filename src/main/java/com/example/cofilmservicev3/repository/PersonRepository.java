package com.example.cofilmservicev3.repository;

import com.example.cofilmservicev3.model.Person;
import com.example.cofilmservicev3.repository.projection.PersonProjection;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long>, JpaSpecificationExecutor<Person> {

    boolean existsByNameAndLastName(String name, String lastName);
    @Query("SELECT person FROM Person person WHERE LOWER(person.name) LIKE %:search% OR LOWER(person.lastName) LIKE %:search%")
    List<PersonProjection> findAllSearch(@Param("search") String searchText, Pageable pageable);

    @Query("SELECT person FROM Person person")
    List<PersonProjection> findAllProjections(Pageable pageable);

    @Query("SELECT person FROM Person person WHERE person.id = :id")
    Optional<PersonProjection> findProjection(@Param("id") Long id);

}
