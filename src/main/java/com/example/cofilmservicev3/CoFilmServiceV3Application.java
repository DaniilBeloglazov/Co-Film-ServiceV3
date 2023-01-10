package com.example.cofilmservicev3;

import com.example.cofilmservicev3.model.Genre;
import com.example.cofilmservicev3.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
public class CoFilmServiceV3Application implements CommandLineRunner {

	private final GenreRepository genreRepository;

	public static void main(String[] args) {
		SpringApplication.run(CoFilmServiceV3Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		 val genreNames = List.of("Action",
				"Western",
				"Gangster Movie",
				"Detective",
				"Drama",
				"Historical Film",
				"Comedy",
				"Melodrama",
				"Musical Film",
				"Noir",
				"Political Film",
				"Adventure Movie",
				"Fairy",
				"Tragedy",
				"Tragicomedy");

		 val genres = genreNames.stream().map(Genre::new).toList();

		genreRepository.saveAll(genres);
	}
}
