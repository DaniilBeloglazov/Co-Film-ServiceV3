package com.example.cofilmservicev3.repository.projection;

import java.util.List;

public interface ShortFilmProjection {

    Long getId();

    String getTitle();

    String getPosterPath();

    List<ShortPersonProjection> getDirectors();

    List<ShortPersonProjection> getWriters();

    List<ShortPersonProjection> getActors();
}
