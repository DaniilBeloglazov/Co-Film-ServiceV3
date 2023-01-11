package com.example.cofilmservicev3.repository.projection;

import com.example.cofilmservicev3.model.Genre;

import java.util.List;

public interface FilmProjection {

    Long getId();

    String getTitle();

    String getDescription();

    Long getProductionYear();

    Double getBudget();

    Double getBoxOffice();

    Long getAudience();

    Long getAgeRating();

    String getAvatarUri();

    List<FilmGenreProjection> getGenres();

    List<FilmPersonProjection> getDirectors();

    List<FilmPersonProjection> getWriters();

    List<FilmPersonProjection> getActors();

}
