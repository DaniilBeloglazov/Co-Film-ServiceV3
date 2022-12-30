package com.example.cofilmservicev3.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity @Table
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Film {

    @Id @GeneratedValue
    private Long id;

    private String title;

    private String posterPath;

    @ManyToMany
    private List<Director> directors;

    @ManyToMany
    private List<Writer> writers;

    @ManyToMany
    private List<Actor> actors;
}
