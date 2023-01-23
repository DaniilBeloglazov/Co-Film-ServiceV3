package com.example.cofilmservicev3.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity @Table
@Setter @Getter
@NoArgsConstructor @AllArgsConstructor
public class Person {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private Double height; // in meters

    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @Transient
    private Long age;

    @Column(nullable = false)
    private String avatarUri;
    @JsonBackReference
    @OneToMany(mappedBy = "owner", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Photo> photos;
    @JsonBackReference
    @ManyToMany(mappedBy = "directors")
    private List<Film> directedFilms;
    @JsonBackReference
    @ManyToMany(mappedBy = "writers")
    private List<Film> writtenFilms;
    @JsonBackReference
    @ManyToMany(mappedBy = "actors")
    private List<Film> actoredFilms;

    private Long getAge() {
        if (dateOfBirth == null)
            return null;
        return (long) LocalDate.now().until(dateOfBirth).getYears();
    }
}
