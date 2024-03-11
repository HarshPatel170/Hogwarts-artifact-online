package com.harsh.hogwartsartifactonline.artifact;

//import com.harsh.hogwartsartifactonline.artifact.utils.IdWorker;
import com.harsh.hogwartsartifactonline.wizard.Wizard;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ArtifactServiceTest {

    @Mock
    ArtifactRepository artifactRepository;

//    @Mock
//    IdWorker idWorker;

    @InjectMocks
    ArtifactService artifactService;

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

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testFindByIdSuccess() {

        // Given. Arrange inputs and targets. Define the behaviour of mock object artifactRepository.
        /*
        "id": "1250808601744904192",
        "name": "Invisibility Cloak",
        "description": "An invisibility cloak is used to make the wearer invisible.",
        "imageUrl": "ImageUrl",
         */
        Artifact a = new Artifact();
        a.setId("1250808601744904192");
        a.setName("Invisibility Cloak");
        a.setDescription("An invisibility cloak is used to make the wearer invisible.");
        a.setImageUrl("ImageUrl");

        Wizard w = new Wizard();
        w.setId(2);
        w.setName("Harry Potter");

        a.setOwner(w);

        given(artifactRepository.findById("1250808601744904192")).willReturn(Optional.of(a)); // Defines the behavior of mock object

        // when. (call the method to tested )Act on the target behavior. When steps should cover the method to be tested.

        Artifact returnArtifact = artifactService.findById("1250808601744904192");

        // Then Assert expected outcome.
        assertThat(returnArtifact.getId()).isEqualTo(a.getId());
        assertThat(returnArtifact.getName()).isEqualTo(a.getName());
        assertThat(returnArtifact.getImageUrl()).isEqualTo(a.getImageUrl());
        assertThat(returnArtifact.getDescription()).isEqualTo(a.getDescription());

        verify(artifactRepository ,
                times(1)).findById("1250808601744904192");

    }

    @Test
    void testFindByIdNotFound(){
        //Given
        given(artifactRepository.findById(Mockito.any(String.class))).willReturn(Optional.empty());

        // When
        Throwable thrown =  catchThrowable(()->{
            Artifact returnArtifact = artifactService.findById("1250808601744904192");
        });

        // Then

        assertThat(thrown)
                .isInstanceOf(ArtifactNotFoundException.class)
                .hasMessage("Could not find artifact with Id 1250808601744904192 :(");
        verify(artifactRepository ,
                times(1)).findById("1250808601744904192");
    }

    @Test
    void testFindAllSuccess() {
        //Given
        given(artifactRepository.findAll()).willReturn(this.artifacts);

        //When
        List<Artifact> actualArtifacts = artifactService.findAll();

        //Then
        assertThat(actualArtifacts.size()).isEqualTo(this.artifacts.size());
        verify(artifactRepository, times(1)).findAll();
    }

    @Test
    void testSaveArtifactSucess() {
        //Given
        Artifact newArtifact = new Artifact();
        newArtifact.setName("It new");
        newArtifact.setDescription("Description..");
        newArtifact.setImageUrl("ImageURl...");

//        given(idWorker.nextId()).willReturn(123456L);
        given(artifactRepository.save(newArtifact)).willReturn(newArtifact);

        //When
        Artifact saveArtifact = artifactService.saveArtifact(newArtifact);

        //Then
        assertThat(saveArtifact.getId()).isEqualTo("123456");
        assertThat(saveArtifact.getName()).isEqualTo(newArtifact.getName());
        assertThat(saveArtifact.getDescription()).isEqualTo(newArtifact.getDescription());
        assertThat(saveArtifact.getImageUrl()).isEqualTo(newArtifact.getImageUrl());
        verify(artifactRepository, times(1)).save(newArtifact);

    }

    @Test
    void testUpdateSuccess() {
    }
}