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
public class Person {

    @Id @GeneratedValue
    private Long id;

    private String name;

    private String lastName;

    private String avatarUri;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<Photo> photos;

    @ManyToMany(mappedBy = "directors")
    private List<Film> directedFilms;

    @ManyToMany(mappedBy = "writers")
    private List<Film> writtenFilms;

    @ManyToMany(mappedBy = "actors")
    private List<Film> actoredFilms;
}
