package com.example.cofilmservicev3.service;

import com.example.cofilmservicev3.exception.PersonAlreadyExistsException;
import com.example.cofilmservicev3.exception.PersonNotFoundException;
import com.example.cofilmservicev3.model.Person;
import com.example.cofilmservicev3.model.Photo;
import com.example.cofilmservicev3.repository.PersonRepository;
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
@Transactional
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    private final ImageService imageService;

    private final EntityMapper beanUtils;

    public List<Person> getAllPersons(Pageable pageable, String query) {

        if (query != null)
            return personRepository.findAllSearch(query, pageable);

        return personRepository.findAllProjections(pageable);
    }

    public Person getPerson(Long id) {

        Person loadedPerson = personRepository.findProjection(id)
                .orElseThrow(() -> new PersonNotFoundException(
                        MessageFormat.format("Person with id: {0} not found", id)));

        return loadedPerson;
    }

    public Long createPerson(Person personToCreate, MultipartFile posterImage) throws IOException {

        if (personRepository.existsByNameAndLastName(personToCreate.getName(), personToCreate.getLastName()))
            throw new PersonAlreadyExistsException(
                    MessageFormat.format("Person with name: {0} and lastName: {1} already exists",
                            personToCreate.getName(),
                            personToCreate.getLastName()));

        String avatarUri = imageService.savePersonPoster(posterImage);
        personToCreate.setAvatarUri(avatarUri);

        return personRepository.save(personToCreate).getId();
    }

    public void updatePersonInfo(Long id, Person updatedPerson, MultipartFile updatedAvatar) throws InvocationTargetException, IllegalAccessException, IOException {

        Person personToUpdate = personRepository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException(
                        MessageFormat.format("Person with id: {0} not found", id)
                ));

        beanUtils.copyProperties(personToUpdate, updatedPerson);

        if (personRepository.existsByNameAndLastName(personToUpdate.getName(), personToUpdate.getLastName()))
            throw new PersonAlreadyExistsException(MessageFormat.format(
                    "Person with name: {0} and lastName{1} already exists. Cant update",
                    personToUpdate.getName(),
                    personToUpdate.getLastName()
            ));

        String avatarUri = imageService.updateImage(personToUpdate.getAvatarUri(), updatedAvatar);

        personToUpdate.setAvatarUri(avatarUri);

        personRepository.save(personToUpdate);
    }

    public void updatePersonPhotos(Long id, MultipartFile[] photos) throws IOException {

        Person personToUpdate = personRepository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException(
                        MessageFormat.format("Person with id: {0} not found", id)
                ));

        List<Photo> personPhotos = personToUpdate.getPhotos();

        imageService.deletePersonPhotos(personPhotos);

        personPhotos.clear();

        for (MultipartFile file : photos) {
            String avatarUri = imageService.savePersonPhoto(file);
            personPhotos.add(new Photo(avatarUri, personToUpdate));
        }

        personRepository.save(personToUpdate);
    }

    public void deletePerson(Long id) {

        if (!personRepository.existsById(id))
            throw new PersonNotFoundException(
                    MessageFormat.format("Person with id: {0} not found. Cant delete", id)
            );

        personRepository.deleteById(id);
    }
}
