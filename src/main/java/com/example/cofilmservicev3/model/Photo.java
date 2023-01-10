package com.example.cofilmservicev3.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity @Table(name = "photos")
@Getter @Setter
@NoArgsConstructor
public class Photo {
    @Id @GeneratedValue
    private Long id;
    private String path;

    @ManyToOne
    private Person owner;

    public Photo(String path, Person owner) {
        this.path = path;
        this.owner = owner;
    }
}
