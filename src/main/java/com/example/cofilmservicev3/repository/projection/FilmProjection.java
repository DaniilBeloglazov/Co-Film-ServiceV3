package com.example.cofilmservicev3.repository.projection;

import java.util.List;

public interface FilmProjection {

    Long getId();

    String getTitle();

    String getPosterPath();

    List<PersonProjection> getDirectors();

    List<PersonProjection> getWriters();

    List<PersonProjection> getActors();

}
