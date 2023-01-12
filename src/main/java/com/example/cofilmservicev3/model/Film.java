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

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT NOT NULL")
    private String description;

    @Column(nullable = false)
    private Long productionYear;

    @Column(nullable = false)
    private Long budget; // бюджет

    @Column(nullable = false)
    private Long boxOffice; // сборы

    @Column(nullable = false)
    private Long audience;

    @Column(nullable = false)
    private Long ageRating;

    @Column(nullable = false)
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
