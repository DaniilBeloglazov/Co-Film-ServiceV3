package com.example.cofilmservicev3.repository.projection;

import java.util.List;

public interface PersonProjection {

    Long getId();

    String getName();

    String getLastName();

    String getPosterPath();

    List<PhotoProjection> getPhotos();
}
