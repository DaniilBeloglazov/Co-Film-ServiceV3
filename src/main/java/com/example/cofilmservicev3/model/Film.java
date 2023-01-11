package com.example.cofilmservicev3.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;

@Entity @Table
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Film {

    @Id @GeneratedValue
    private Long id;

    private String title;

    private String description;

    private Long productionYear;

    private Long budget; // бюджет

    private Long boxOffice; // сборы

    private Long audience;

    private Long ageRating;

    private String avatarUri;

    @ManyToMany
    private List<Genre> genres;

    @ManyToMany
    private List<Person> directors;

    @ManyToMany
    private List<Person> writers;

    @ManyToMany
    private List<Person> actors;
}
