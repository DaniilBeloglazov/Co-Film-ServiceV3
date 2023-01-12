package com.example.cofilmservicev3.repository.projection;

import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;
import java.util.List;

public interface PersonProjection {

    Long getId();

    String getName();

    String getLastName();

    String getAvatarUri();

    Double getHeight();

    LocalDate getDateOfBirth();

    @Value("#{target.getDateOfBirth().until(T(java.time.LocalDate).now()).getYears()}")
    Long getAge();

    List<PhotoProjection> getPhotos();

    List<PersonFilmProjection> getDirectedFilms();

    List<PersonFilmProjection> getWrittenFilms();

    List<PersonFilmProjection> getActoredFilms();
}
