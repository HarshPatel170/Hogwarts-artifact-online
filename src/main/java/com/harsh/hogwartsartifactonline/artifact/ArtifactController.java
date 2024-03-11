package com.harsh.hogwartsartifactonline.artifact;

import com.harsh.hogwartsartifactonline.artifact.converter.ArtifactToArtifactDtoConverter;
import com.harsh.hogwartsartifactonline.artifact.dtao.ArtifactDto;
import com.harsh.hogwartsartifactonline.system.Result;
import com.harsh.hogwartsartifactonline.system.StatusCode;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ArtifactController {

    private final ArtifactService artifactService;

    private final ArtifactToArtifactDtoConverter artifactToArtifactDtoConverter;

    public ArtifactController(ArtifactService artifactService, ArtifactToArtifactDtoConverter artifactToArtifactDtoConverter) {
        this.artifactService = artifactService;
        this.artifactToArtifactDtoConverter = artifactToArtifactDtoConverter;
    }


    @GetMapping("/api/v1/artifacts/{artifactId}")
    public Result findArtifactById(@PathVariable String artifactId){
        Artifact foundArtifact = this.artifactService.findById(artifactId);
        ArtifactDto artifactDto = this.artifactToArtifactDtoConverter.convert(foundArtifact);
        return new Result(true, StatusCode.SUCCESS,"Find one Success",artifactDto);
    }

    @GetMapping("/api/v1/artifacts")
    public Result findAllArtifacts(){
        List<Artifact> foundArtifacts = this.artifactService.findAll();
        List<ArtifactDto> artifactDtos = foundArtifacts.stream().map(foundArtifact -> this.artifactToArtifactDtoConverter.convert(foundArtifact)).collect(Collectors.toList());
        return new Result(true, StatusCode.SUCCESS,"Find All Success",artifactDtos);

    }

    @PostMapping("/api/v1/artifacts")
    public Result addArtifact(@RequestBody ArtifactDto artifactDto){

        return null;
    }

    @PutMapping()
    public Result updateArtifact(@PathVariable String artifactId ,@Valid @RequestBody ArtifactDto artifactDto){
        return null;
    }
}
