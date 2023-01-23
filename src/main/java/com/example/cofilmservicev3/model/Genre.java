package com.example.cofilmservicev3.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity @Table
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class Genre {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @JsonBackReference
    @ManyToMany(mappedBy = "genres")
    private List<Film> films;

    public Genre(String name) {
        this.name = name;
    }
}
