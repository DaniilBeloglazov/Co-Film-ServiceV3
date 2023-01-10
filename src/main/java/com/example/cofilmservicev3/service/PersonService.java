package com.example.cofilmservicev3.service;

import com.example.cofilmservicev3.exception.PersonNotFoundException;
import com.example.cofilmservicev3.model.Person;
import com.example.cofilmservicev3.model.Photo;
import com.example.cofilmservicev3.repository.PersonRepository;
import com.example.cofilmservicev3.repository.projection.PersonProjection;
import com.example.cofilmservicev3.utility.EntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    private final ImageService imageService;

    private final EntityMapper beanUtils;

    public List<PersonProjection> getAllPersons(Pageable pageable) {

        return personRepository.findAllProjections(pageable);
    }

    public PersonProjection getPerson(Long id) {

        return personRepository.findProjection(id);
    }

    public void createPerson(Person personToCreate, MultipartFile posterImage) throws IOException {

        String posterPath = imageService.savePersonPoster(posterImage);
        personToCreate.setPosterPath(posterPath);

        personRepository.save(personToCreate);
    }

    public void updatePersonInfo(Long id, Person updatedPerson) throws InvocationTargetException, IllegalAccessException {

        Person personToUpdate = personRepository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException(
                        MessageFormat.format("Person with id: {0} not found", id)
                ));

        beanUtils.copyProperties(personToUpdate, updatedPerson);

        personRepository.save(personToUpdate);
    }

    public void updatePersonPhotos(Long id, MultipartFile[] photos) throws IOException {



        Person personToUpdate = personRepository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException(
                        MessageFormat.format("Person with id: {0} not found", id)
                ));

        List<Photo> personPhotos = personToUpdate.getPhotos();

        for (Photo photo : personPhotos) {
            Files.deleteIfExists(Path.of(photo.getPath()));
        }

        personPhotos.clear();
        for (MultipartFile file : photos) {
            String posterPath = imageService.savePersonPhoto(file);
            personPhotos.add(new Photo(posterPath, personToUpdate));
        }

        personRepository.save(personToUpdate);
    }
}
