package com.harsh.hogwartsartifactonline.artifact;

//import com.harsh.hogwartsartifactonline.artifact.utils.IdWorker;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ArtifactService {

//    private final IdWorker idWorker;

    private final ArtifactRepository artifactRepository;

    public ArtifactService(ArtifactRepository artifactRepository) {
        this.artifactRepository = artifactRepository;
    }

//    public ArtifactService(IdWorker idWorker, ArtifactRepository artifactRepository) {
//        this.idWorker = idWorker;
//        this.artifactRepository = artifactRepository;
//    }


    public Artifact findById(String artifactId){
        return this.artifactRepository.findById(artifactId)
                .orElseThrow(() -> new ArtifactNotFoundException(artifactId));
    }

    public List<Artifact> findAll(){
        return this.artifactRepository.findAll();
    }

    public Artifact saveArtifact(Artifact newArtifact){
//        newArtifact.setId(idWorker.nextId() + "");
        return this.artifactRepository.save(newArtifact);
    }

    public Artifact update(String artifactId , Artifact artifact){
        return null;
    }
}
