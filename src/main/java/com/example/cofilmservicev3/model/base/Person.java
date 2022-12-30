package com.example.cofilmservicev3.model.base;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity @Table
@Inheritance(strategy = InheritanceType.JOINED)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Person {

    @Id @GeneratedValue
    private Long id;

    private String name;

    private String lastName;

    private String posterPath;

    @OneToMany(mappedBy = "owner")
    private List<Photo> photos;

}
