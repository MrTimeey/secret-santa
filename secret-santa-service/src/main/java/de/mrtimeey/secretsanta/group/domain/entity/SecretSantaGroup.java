package de.mrtimeey.secretsanta.group.domain.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;

@Document
@Builder
@Data
public class SecretSantaGroup {

    @Id
    private String id;

    @NotBlank
    private String title;

    @Builder.Default
    private boolean released = false;
}
