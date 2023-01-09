package com.example.cofilmservicev3.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data @NoArgsConstructor
public class UpdateFilmMetadataRequest {

    private String title;

    private List<Long> directors;

    private List<Long> writers;

    private List<Long> actors;
}
