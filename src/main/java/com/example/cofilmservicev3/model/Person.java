package com.example.cofilmservicev3.model;

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

    @Id @GeneratedValue
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

    @OneToMany(mappedBy = "owner", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Photo> photos;

    @ManyToMany(mappedBy = "directors")
    private List<Film> directedFilms;

    @ManyToMany(mappedBy = "writers")
    private List<Film> writtenFilms;

    @ManyToMany(mappedBy = "actors")
    private List<Film> actoredFilms;

    private Long getAge() {
        return (long) LocalDate.now().until(dateOfBirth).getYears();
    }
}
