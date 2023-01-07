package com.example.cofilmservicev3.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data @NoArgsConstructor
public class CreateFilmRequest {

    private String title;

    private MultipartFile posterImage;

    private List<Long> directors;
    
    private List<Long> writers;

    private List<Long> actors;

}
