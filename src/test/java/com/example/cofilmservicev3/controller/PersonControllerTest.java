package com.example.cofilmservicev3.controller;

import com.example.cofilmservicev3.dto.CreatePersonRequest;
import com.example.cofilmservicev3.dto.CreatePersonResponse;
import com.example.cofilmservicev3.model.Person;
import com.example.cofilmservicev3.repository.projection.PersonProjection;
import com.example.cofilmservicev3.service.PersonService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.projection.CollectionAwareProjectionFactory;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.servlet.http.Part;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;

@TestPropertySource("classpath:application-test.yml")
@WebMvcTest(PersonController.class)
public class PersonControllerTest {
    public PersonControllerTest() throws IOException {
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    private PersonService personService;

    private final ProjectionFactory factory = new CollectionAwareProjectionFactory();

    private final String baseUrl = "/api/v1";
    private Person mockPerson;
    private final MockMultipartFile mockImage = new MockMultipartFile(
            "poster", "testImage.jpeg", MediaType.MULTIPART_FORM_DATA_VALUE, new ClassPathResource("static/testImage.jpeg").getInputStream());

    private CreatePersonRequest mockCreateRequest;

    @BeforeEach
    public void beforeEachCall() throws IOException {

        objectMapper.registerModule(new JavaTimeModule());

        this.mockPerson = new Person(
                16L, "TestName", "TestLastName", 180.0, LocalDate.of(2000, 12, 17),
                null, "/test/image/test.png", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

        this.mockCreateRequest = new CreatePersonRequest(
                "TestName", "TestLastName", mockImage, 200L, LocalDate.of(17, 12, 12));

    }

    @Test
    public void invalidIdArgument() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get(baseUrl + "/persons/qweqwe"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
        System.out.println("======================================================================================");

        mockMvc.perform(MockMvcRequestBuilders.patch(baseUrl + "/persons/qweqwe")
                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
        System.out.println("======================================================================================");

        mockMvc.perform(MockMvcRequestBuilders.multipart(
                                HttpMethod.PUT, baseUrl + "/persons/qweqwe/photos")
                        .file(mockImage))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
        System.out.println("======================================================================================");

        mockMvc.perform(MockMvcRequestBuilders.delete(baseUrl + "/persons/qweqwe"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void listPersonTest() throws Exception {

        Mockito.when(personService.getPerson(mockPerson.getId()))
                .thenReturn(factory.createProjection(PersonProjection.class, mockPerson));

        mockMvc.perform(MockMvcRequestBuilders.get(baseUrl + "/persons/16"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("" +
                        "{\n" +
                        "  \"id\": 16,\n" +
                        "  \"name\": \"TestName\",\n" +
                        "  \"avatarUri\": \"/test/image/test.png\",\n" +
                        "  \"lastName\": \"TestLastName\",\n" +
                        "  \"photos\": [],\n" +
                        "  \"age\": 22,\n" +
                        "  \"height\": 180,\n" +
                        "  \"dateOfBirth\": [\n" +
                        "    2000,\n" +
                        "    12,\n" +
                        "    17\n" +
                        "  ],\n" +
                        "  \"directedFilms\": [],\n" +
                        "  \"writtenFilms\": [],\n" +
                        "  \"actoredFilms\": []\n" +
                        "}\n"));
    }

    @Test
    public void addPersonTest() throws Exception {

        Mockito.when(modelMapper.map(mockCreateRequest, Person.class)).thenReturn(mockPerson);
        Mockito.when(personService.createPerson(mockPerson, mockImage)).thenReturn(228L);

        val requestParams = convertToMultiValueMap(mockCreateRequest);

        mockMvc.perform(MockMvcRequestBuilders.multipart(HttpMethod.POST, baseUrl + "/person")
                        .file(mockImage)
                        .params(requestParams))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status()
                        .isCreated())
                .andExpect(MockMvcResultMatchers.content().json("" +
                        "{\n" +
                        "  \"id\": 228\n" +
                        "}"));


    }

    private <T> MultiValueMap<String, String> convertToMultiValueMap(T object) {
        Map<String, String> fieldMap = objectMapper.convertValue(object, new TypeReference<>() {});
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.setAll(fieldMap);
        return multiValueMap;
    }

}