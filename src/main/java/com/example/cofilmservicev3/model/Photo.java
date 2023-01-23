package com.example.cofilmservicev3.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity @Table(name = "photos")
@Getter @Setter
@NoArgsConstructor
public class Photo {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String uri;

    @ManyToOne(fetch = FetchType.LAZY)
    private Person owner;

    public Photo(String path, Person owner) {
        this.uri = path;
        this.owner = owner;
    }
}
