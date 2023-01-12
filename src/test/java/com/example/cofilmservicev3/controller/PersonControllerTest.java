package com.example.cofilmservicev3.controller;

import com.example.cofilmservicev3.model.Person;
import com.example.cofilmservicev3.repository.projection.PersonProjection;
import com.example.cofilmservicev3.service.PersonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.projection.CollectionAwareProjectionFactory;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;

@WebMvcTest(PersonController.class)
public class PersonControllerTest {
    public PersonControllerTest() throws IOException {
    }
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper jsonMapper;

    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    private PersonService personService;

    private ProjectionFactory factory = new CollectionAwareProjectionFactory();

    private final String baseUrl = "/api/v1";
    private final Person mockPerson = new Person(
            16L, "TestName", "TestLastName", 180.0, LocalDate.of(2000,12,17),
            22L, "/test/image/test.png", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    private final MockMultipartFile mockImage = new MockMultipartFile(
            "image",
            "hello.png",
            MediaType.MULTIPART_FORM_DATA_VALUE,
            Files.readAllBytes(Path.of("/home/user/Documents/Education/Co-Film-ServiceV3/src/main/resources/static/testImage.jpeg")));

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
                HttpMethod.PUT,baseUrl + "/persons/qweqwe/photos")
                        .file(mockImage))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
        System.out.println("======================================================================================");

        mockMvc.perform(MockMvcRequestBuilders.delete(baseUrl + "/persons/qweqwe"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void listPersonsTest() throws Exception {

        Mockito.when(personService.getPerson(16L)).thenReturn(factory.createProjection(PersonProjection.class, mockPerson));

        val expectedContent = jsonMapper.writeValueAsString(factory.createProjection(PersonProjection.class, mockPerson));

        mockMvc.perform(MockMvcRequestBuilders.get(baseUrl + "/persons/16"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status()
                        .isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedContent)
                );
    }

}