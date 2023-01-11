package com.example.cofilmservicev3.repository.projection;

import java.util.List;

public interface PersonProjection {

    Long getId();

    String getName();

    String getLastName();

    String getAvatarUri();

    List<PhotoProjection> getPhotos();

    List<PersonFilmProjection> getDirectedFilms();

    List<PersonFilmProjection> getWrittenFilms();

    List<PersonFilmProjection> getActoredFilms();
}
