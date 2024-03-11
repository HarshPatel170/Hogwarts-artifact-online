package com.harsh.hogwartsartifactonline.artifact;

import com.harsh.hogwartsartifactonline.artifact.dtao.ArtifactDto;
import com.harsh.hogwartsartifactonline.system.StatusCode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;



import static org.mockito.BDDMockito.given;
////\

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;


import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class ArtifactControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ArtifactService artifactService;

    @Autowired
    ObjectMapper objectMapper;

    List<Artifact> artifacts;

    @BeforeEach
    void setUp() {
        this.artifacts = new ArrayList<>();

        Artifact a1 = new Artifact();
        a1.setId("1250808601744904192");
        a1.setName("Invisibility Cloak");
        a1.setDescription("An invisibility cloak is used to make the wearer invisible.");
        a1.setImageUrl("ImageUrl");
        this.artifacts.add(a1);

        Artifact a2 = new Artifact();
        a2.setId("1250808601744904191");
        a2.setName("Deluminator");
        a2.setDescription("A Deluminator is a device invented by Albus Dumbledore that resembles a cigarette lighter. It is used to remove or absorb (as well as return) the light from any light source to provide cover to the user.");
        a2.setImageUrl("ImageUrl1");
        this.artifacts.add(a2);


        Artifact a3 = new Artifact();
        a3.setId("1250808601744904193");
        a3.setName("Elder Wand");
        a3.setDescription("The Elder Wand, known throughout history as the Deathstick or the Wand of Destiny, is an extremely powerful wand made of elder wood with a core of Thestral tail hair.");
        a3.setImageUrl("ImageUrl2");
        this.artifacts.add(a3);

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testFindArtifactByIdSuccess() throws Exception {
        //Given
        given(this.artifactService.findById("1250808601744904191")).willReturn(this.artifacts.get(1));

        //when and then
      this.mockMvc.perform(get("/api/v1/artifacts/1250808601744904191").accept(MediaType.APPLICATION_JSON))
              .andExpect(jsonPath("$.flag").value(true))
              .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
              .andExpect(jsonPath("$.message").value("Find one Success"))
              .andExpect(jsonPath("$.data.id").value("1250808601744904191"))
              .andExpect(jsonPath("$.data.name").value("Deluminator"));



    }

    @Test
    void testFindArtifactByIdNotFound() throws Exception {
        //Given
        given(this.artifactService.findById("1250808601744904191")).willThrow(new ArtifactNotFoundException("1250808601744904191"));

        //when and then
        this.mockMvc.perform(get("/api/v1/artifacts/1250808601744904191").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not find artifact with Id 1250808601744904191 :("))
                .andExpect(jsonPath("$.data").isEmpty());



    }

    @Test
    void testFindArtifactAllSuccess() throws Exception {
        //Given
        given(this.artifactService.findAll()).willReturn(this.artifacts);

        //When and Then

        this.mockMvc.perform(get("/api/v1/artifacts").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Find All Success"))
                .andExpect(jsonPath("$.data", Matchers.hasSize(this.artifacts.size())))
                .andExpect(jsonPath("$.data[0].id").value("1250808601744904192"))
                .andExpect(jsonPath("$.data[0].name").value("Invisibility Cloak"))
                .andExpect(jsonPath("$.data[1].id").value("1250808601744904191"))
                .andExpect(jsonPath("$.data[1].name").value("Deluminator"));


    }
    @Test
    void testSaveArtifactSucess() throws Exception {
        //Given
        ArtifactDto artifactDto = new ArtifactDto(null,"art1","Tghis is new","ImageUrl.",null);
        String json = this.objectMapper.writeValueAsString(artifactDto);

        Artifact saveArtifact = new Artifact();
        saveArtifact.setId("23");
        saveArtifact.setName("art1");
        saveArtifact.setDescription("Tghis is new");
        saveArtifact.setImageUrl("ImageUrl.");

        given(this.artifactService.saveArtifact(Mockito.any(Artifact.class))).willReturn(saveArtifact);

        // When and Then
        this.mockMvc.perform(post("/api/v1/artifacts").contentType(MediaType.APPLICATION_JSON).content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Add Success"))
                .andExpect(jsonPath("$.data.id").isNotEmpty())
                .andExpect(jsonPath("$.data.name").value(saveArtifact.getName()))
                .andExpect(jsonPath("$.data.description").value(saveArtifact.getDescription()))
                .andExpect(jsonPath("$.data.imageUrl").value(saveArtifact.getImageUrl()));

    }
}