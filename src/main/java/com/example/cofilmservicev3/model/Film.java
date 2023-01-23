package com.example.cofilmservicev3.model;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import java.util.List;

@Entity @Table
@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class Film {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private String posterUri;
    @JsonIdentityReference
    @ManyToMany
    private List<Genre> genres;

    @JsonIdentityReference
    @ManyToMany
    private List<Person> directors;

    @JsonIdentityReference
    @ManyToMany
    private List<Person> writers;

    @JsonIdentityReference
    @ManyToMany
    private List<Person> actors;
}
