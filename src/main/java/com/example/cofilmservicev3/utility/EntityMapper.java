package com.example.cofilmservicev3.utility;

import com.example.cofilmservicev3.exception.GenreNotFoundException;
import com.example.cofilmservicev3.exception.PersonNotFoundException;
import com.example.cofilmservicev3.model.Genre;
import com.example.cofilmservicev3.model.Person;
import com.example.cofilmservicev3.repository.GenreRepository;
import com.example.cofilmservicev3.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class EntityMapper extends BeanUtilsBean {

    private final GenreRepository genreRepository;
    private final PersonRepository personRepository;

    @Override
    public void copyProperty(Object dest, String name, Object value)
            throws IllegalAccessException, InvocationTargetException {
        if(value == null || (value instanceof List<?> && (((List<?>) value).isEmpty()))) return; // Do not copy null values
//        if(value instanceof String && StringUtils.isBlank((String)value)) {
//            log.debug("Skip blank on copy");
//            return;
//        } // Do not copy blank values
        if (name.equals("genres")) {
            List<Genre> genre = (List<Genre>) value;
            value = genre.stream().map((genreForMap) -> genreRepository.findById(genreForMap.getId())
                    .orElseThrow(() -> new GenreNotFoundException("Genre not found"))).toList();
        }

        if (name.equals("directors") || name.equals("writers") || name.equals("actors")){
            List<Person> person = (List<Person>) value;
            value = person.stream().map((personForMap) -> personRepository.findById(personForMap.getId())
                    .orElseThrow(() -> new PersonNotFoundException("Person not found"))).toList();
        }
        super.copyProperty(dest, name, value);
    }
}
