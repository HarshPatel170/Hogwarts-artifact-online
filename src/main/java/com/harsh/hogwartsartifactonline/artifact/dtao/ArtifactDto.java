package com.harsh.hogwartsartifactonline.artifact.dtao;

import com.harsh.hogwartsartifactonline.wizard.dtao.WizardDto;

public record ArtifactDto(String id,
                          String name ,
                          String description,
                          String imageUrl,
                          WizardDto owner) {
}
