package com.example.cofilmservicev3.service;

import com.example.cofilmservicev3.exception.FilmNotFoundException;
import com.example.cofilmservicev3.exception.PersonNotFoundException;
import com.example.cofilmservicev3.model.Film;
import com.example.cofilmservicev3.repository.FilmRepository;
import com.example.cofilmservicev3.repository.GenreRepository;
import com.example.cofilmservicev3.repository.PersonRepository;
import com.example.cofilmservicev3.repository.projection.FilmProjection;
import com.example.cofilmservicev3.utility.EntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FilmService {

    private final ImageService imageService;
    private final FilmRepository filmRepository;
    private final PersonRepository personRepository;

    private final GenreRepository genreRepository;

    private final EntityMapper beanUtils;

    public List<FilmProjection> getAllFilms(Pageable pageable) {

        List<FilmProjection> filmProjections = filmRepository.shortFindAll(pageable);

        return filmProjections;
    }

    public FilmProjection getFilm(Long id) {

        return filmRepository.shortFindById(id);
    }

    @Transactional
    public void createFilm(Film filmToCreate, MultipartFile posterImage) throws IOException {

        filmToCreate.getDirectors().forEach(person -> personRepository.findById(person.getId())
                .orElseThrow(() -> new PersonNotFoundException(
                        MessageFormat.format("Person with id: {0} not found", person.getId())
                )));

        filmToCreate.getWriters().forEach(person -> personRepository.findById(person.getId())
                .orElseThrow(() -> new PersonNotFoundException(
                        MessageFormat.format("Person with id: {0} not found", person.getId())
                )));

        filmToCreate.getActors()
                .forEach(person -> personRepository.findById(person.getId())
                        .orElseThrow(() -> new PersonNotFoundException(
                                MessageFormat.format("Person with id: {0} not found", person.getId())
                        )));

        String posterPath = imageService.saveFilmPoster(posterImage);
        filmToCreate.setPosterPath(posterPath);

        filmRepository.save(filmToCreate);
    }

    public void updateFilm(Long id, Film updatedFilm, MultipartFile updatedPoster) throws IOException, InvocationTargetException, IllegalAccessException {

        Film filmToUpdate = filmRepository.findById(id)
                .orElseThrow(() -> new FilmNotFoundException(
                        MessageFormat.format("Film with id: {0} not found", id)));

        String posterPath = imageService.updateImage(filmToUpdate.getPosterPath(), updatedPoster);

        beanUtils.copyProperties(filmToUpdate, updatedFilm);

//        updateRelations(filmToUpdate);
        filmToUpdate.setPosterPath(posterPath);

        filmRepository.save(filmToUpdate);
    }

//    private void updateRelations(Film filmToUpdate) {
//
//        if (filmToUpdate.getGenres() != null)
//            for (Genre genre : filmToUpdate.getGenres()) {
//                genre = genreRepository.findById(genre.getId())
//                        .orElseThrow(() -> new PersonNotFoundException(
//                                MessageFormat.format("Director with id: {0} not found", genre.getId())
//                        ));
//            }
//        if (filmToUpdate.getDirectors() != null)
//            filmToUpdate.setDirectors(filmToUpdate.getDirectors()
//                    .stream()
//                    .map((director) -> personRepository.findById(director.getId())
//                            .orElseThrow(() -> new PersonNotFoundException(
//                                    MessageFormat.format("Director with id: {0} not found", director.getId())
//                            )))
//                    .toList()
//            );
//        if (filmToUpdate.getWriters() != null)
//            filmToUpdate.setWriters(filmToUpdate.getWriters()
//                    .stream()
//                    .map((writer) -> personRepository.findById(writer.getId())
//                            .orElseThrow(() -> new PersonNotFoundException(
//                                    MessageFormat.format("Writer with id: {0} not found", writer.getId())
//                            )))
//                    .toList()
//            );
//        if (filmToUpdate.getActors() != null)
//            filmToUpdate.setActors(filmToUpdate.getActors()
//                    .stream()
//                    .map((actor) -> personRepository.findById(actor.getId())
//                            .orElseThrow(() -> new PersonNotFoundException(
//                                    MessageFormat.format("Actor with id: {0} not found", actor.getId())
//                            )))
//                    .toList()
//            );
//    }

    public void deleteFilm(Long id) {

        if (!filmRepository.existsById(id))
            throw new FilmNotFoundException(MessageFormat.format("Film with id: {0} not found", id));

        filmRepository.deleteById(id);
    }
}
