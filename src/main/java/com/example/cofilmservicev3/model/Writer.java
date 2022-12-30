package com.example.cofilmservicev3.model;

import com.example.cofilmservicev3.model.base.Person;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity @Table
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Writer extends Person {

    @ManyToMany
    private List<Film> directedFilms;
}
