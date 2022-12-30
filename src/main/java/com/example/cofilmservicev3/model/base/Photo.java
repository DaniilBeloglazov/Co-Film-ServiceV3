package com.example.cofilmservicev3.model.base;

import com.example.cofilmservicev3.model.base.Person;
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
}
